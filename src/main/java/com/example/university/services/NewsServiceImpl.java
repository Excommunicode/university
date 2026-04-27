package com.example.university.services;

import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.News;
import com.example.university.repositories.NewsRepository;
import com.example.university.services.contract.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    @Transactional
    public News createNews(News news) {
        log.info("createNews called: title='{}', published={}", news.getTitle(), news.getPublished());
        return newsRepository.save(news);
    }

    @Override
    public News findNews(Long id) {
        log.info("findNews called: id={}", id);
        return newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with id: " + id));
    }

    @Override
    public Page<News> getAllNews(Pageable pageable) {
        log.info("getAllNews called: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return newsRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public News updateNews(Long id, News news) {
        log.info("updateNews called: id={}, title='{}'", id, news.getTitle());
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
        log.info("deleteNews called: id={}", id);
        if (!newsRepository.existsById(id)) {
            throw new ResourceNotFoundException("News not found with id: " + id);
        }
        newsRepository.deleteById(id);
    }

    @Override
    public Page<News> getPublishedNews(Pageable pageable) {
        log.info("getPublishedNews called: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return newsRepository.findByPublishedTrue(pageable);
    }
}
