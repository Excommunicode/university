package com.example.university.services.contract;

import com.example.university.models.News;

import java.util.List;

public interface NewsService {

    News createNews(News news);

    News findNews(Long id);

    List<News> getAllNews();

    News updateNews(Long id, News news);

    void deleteNews(Long id);

    List<News> getPublishedNews();
}
