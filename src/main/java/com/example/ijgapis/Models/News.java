package com.example.ijgapis.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_posted", nullable = false, updatable = false)
    private LocalDateTime datePosted;

    // Pre-persist hook to set the datePosted automatically
    @PrePersist
    protected void onCreate() {
        this.datePosted = LocalDateTime.now();
    }
}
