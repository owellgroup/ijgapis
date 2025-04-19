package com.example.ijgapis.Services;

import com.example.ijgapis.Models.News;
import com.example.ijgapis.Repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    public News createNews(News news) {
        return newsRepository.save(news);
    }

    public News updateNews(Long id, News updatedNews) {
        return newsRepository.findById(id).map(news -> {
            news.setTitle(updatedNews.getTitle());
            news.setDescription(updatedNews.getDescription());
            return newsRepository.save(news);
        }).orElseThrow(() -> new RuntimeException("News not found with id: " + id));
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}