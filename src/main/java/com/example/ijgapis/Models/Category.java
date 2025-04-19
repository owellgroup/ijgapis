package com.example.ijgapis.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, unique = true)
    private String name;
}