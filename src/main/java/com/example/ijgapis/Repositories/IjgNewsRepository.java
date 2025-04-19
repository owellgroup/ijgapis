package com.example.ijgapis.Repositories;

import com.example.ijgapis.Models.IjgNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IjgNewsRepository extends JpaRepository<IjgNews, Long> {
}