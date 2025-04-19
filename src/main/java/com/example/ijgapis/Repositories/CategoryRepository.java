package com.example.ijgapis.Repositories;

import com.example.ijgapis.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}