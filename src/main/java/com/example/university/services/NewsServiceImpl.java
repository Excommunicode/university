package com.example.university.services;

import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.News;
import com.example.university.repositories.NewsRepository;
import com.example.university.services.contract.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    @Transactional
    public News createNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News findNews(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + id));
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    @Transactional
    public News updateNews(Long id, News news) {
        News existing = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + id));
        existing.setTitle(news.getTitle());
        existing.setContent(news.getContent());
        existing.setPublished(news.getPublished());
        return newsRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new ResourceNotFoundException("News not found with id: " + id);
        }
        newsRepository.deleteById(id);
    }

    @Override
    public List<News> getPublishedNews() {
        return newsRepository.findByPublishedTrue();
    }
}
