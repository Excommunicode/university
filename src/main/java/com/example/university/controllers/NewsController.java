package com.example.university.controllers;

import com.example.university.dto.NewsCreateDto;
import com.example.university.models.News;
import com.example.university.services.contract.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<News>> getAllNews(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(newsService.getAllNews(pageable));
    }

    @GetMapping("/published")
    public ResponseEntity<Page<News>> getPublishedNews(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(newsService.getPublishedNews(pageable));
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
