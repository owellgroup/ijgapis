package com.example.ijgapis.Services;


import com.example.ijgapis.Models.Category;
import com.example.ijgapis.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(Category category) {
        Category oldCategory = categoryRepository.findById(category.getId()).orElse(null);
        oldCategory.setName(category.getName());
        return categoryRepository.save(oldCategory);
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }
}