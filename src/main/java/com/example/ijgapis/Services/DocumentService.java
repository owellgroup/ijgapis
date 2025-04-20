package com.example.ijgapis.Services;
import com.example.ijgapis.Models.Category;
import com.example.ijgapis.Models.Document;
import com.example.ijgapis.Repositories.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.net.MalformedURLException;



@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private CategoryService categoryService;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";
    // Method to upload a document
    public Document uploadDocument(MultipartFile file, String title, String description, String categoryId) throws IOException {
        // Validate file type
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("application/pdf") 
            && !contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
            && !contentType.equals("application/vnd.ms-excel"))) {
            throw new IllegalArgumentException("Only PDF and Excel files are allowed");
        }

        // Ensure upload directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // Save the file with timestamp to handle duplicates
        String originalFileName = file.getOriginalFilename();
        String fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.')) 
            + "_" + System.currentTimeMillis() 
            + originalFileName.substring(originalFileName.lastIndexOf('.'));
        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // Generate a public URL for the file
        String fileUrl = "https://ijg-research-admin.vercel.app/api/documents/view/" + fileName;

        // Create document entity
        Document document = new Document();
        document.setTitle(title);
        document.setDescription(description);
        document.setFileType(contentType);
        document.setFileUrl(fileUrl);
        document.setDatePosted(LocalDate.now());

        // Associate with category
        Category category = categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        document.setCategory(category);

        return documentRepository.save(document);
    }

//load file
// Method to load a file as a resource
public Resource loadFileAsResource(String fileName) throws MalformedURLException {
    try {
        Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName).normalize();
        
        // Ensure parent directory exists
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        // Security check
        if (!filePath.startsWith(uploadPath)) {
            throw new RuntimeException("Invalid file path - attempted directory traversal");
        }

        if (!Files.exists(filePath)) {
            throw new RuntimeException("File not found: " + fileName);
        }

        if (!Files.isReadable(filePath)) {
            throw new RuntimeException("File is not readable: " + fileName);
        }

        Resource resource = new UrlResource(filePath.toUri());
        return resource;

    } catch (MalformedURLException e) {
        throw new RuntimeException("Malformed file URL for: " + fileName + ". Error: " + e.getMessage());
    } catch (Exception e) {
        throw new RuntimeException("Failed to load file: " + fileName + ". Error: " + e.getMessage());
    }
}

    public List<Document> getDocumentsByCategory(String categoryId) {
        return documentRepository.findByCategoryId(categoryId);
    }

    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    //Update method here
    // Update method
    public Document updateDocument(Long id, MultipartFile file, String title, String description, String categoryId) throws IOException {
        Document existingDocument = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        // Update fields
        if (file != null && !file.isEmpty()) {
            // Save the file with timestamp to handle duplicates
            String originalFileName = file.getOriginalFilename();
            String fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.')) 
                + "_" + System.currentTimeMillis() 
                + originalFileName.substring(originalFileName.lastIndexOf('.'));
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
            Files.copy(file.getInputStream(), filePath);

            String fileUrl = "https://ijg-research-admin.vercel.app/api/documents/view/" + fileName;
            existingDocument.setFileUrl(fileUrl);
            existingDocument.setFileType(file.getContentType());
        }

        existingDocument.setTitle(title);
        existingDocument.setDescription(description);
        existingDocument.setDatePosted(LocalDate.now());

        // Update category if provided
        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingDocument.setCategory(category);
        }

        return documentRepository.save(existingDocument);
    }

    // Delete method
    public void deleteDocument(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        documentRepository.delete(document);
    }


}
