package com.example.ijgapis.Repositories;

import com.example.ijgapis.Models.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCategoryId(String categoryId);
}