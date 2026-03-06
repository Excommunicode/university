package com.example.university.services.contract;

import com.example.university.models.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {

    News createNews(News news);

    News findNews(Long id);

    Page<News> getAllNews(Pageable pageable);

    News updateNews(Long id, News news);

    void deleteNews(Long id);

    Page<News> getPublishedNews(Pageable pageable);
}
