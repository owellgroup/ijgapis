package com.example.ijgapis.Repositories;

import com.example.ijgapis.Models.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}