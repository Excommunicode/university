package com.example.university.controllers;

import com.example.university.dto.NewsCreateDto;
import com.example.university.models.News;
import com.example.university.services.contract.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<News> createNews(@Valid @RequestBody NewsCreateDto dto) {
        News news = new News();
        news.setTitle(dto.getTitle());
        news.setContent(dto.getContent());
        news.setPublished(dto.getPublished() != null ? dto.getPublished() : false);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsService.createNews(news));
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> findNews(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.findNews(id));
    }

    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping("/published")
    public ResponseEntity<List<News>> getPublishedNews() {
        return ResponseEntity.ok(newsService.getPublishedNews());
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(@PathVariable Long id,
                                           @Valid @RequestBody NewsCreateDto dto) {
        News news = new News();
        news.setTitle(dto.getTitle());
        news.setContent(dto.getContent());
        news.setPublished(dto.getPublished());
        return ResponseEntity.ok(newsService.updateNews(id, news));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }
}
