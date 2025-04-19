package com.example.ijgapis.Services;

import com.example.ijgapis.Models.IjgNews;
import com.example.ijgapis.Repositories.IjgNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IjgNewsService {

    @Autowired
    private IjgNewsRepository ijgNewsRepository;
    //get the news
    public IjgNews getIjgNewsById(IjgNews ijgNews) {
        return ijgNewsRepository.findById(ijgNews.getId()).get();
    }

    public List<IjgNews> getAllIjgNews() {
        return ijgNewsRepository.findAll();
    }

    public IjgNews saveIjgNews(IjgNews ijgNews) {
        return ijgNewsRepository.save(ijgNews);
    }

    public void deleteIjgNews(IjgNews ijgNews) {
        ijgNewsRepository.delete(ijgNews);
    }

    public IjgNews updateIjgNews(IjgNews ijgNews) {
        IjgNews oldIjgNews = getIjgNewsById(ijgNews);
        oldIjgNews.setImage(ijgNews.getImage());
        oldIjgNews.setTitle(ijgNews.getTitle());
        oldIjgNews.setImageUrl(ijgNews.getImageUrl());
        return ijgNewsRepository.save(oldIjgNews);

    }
}
