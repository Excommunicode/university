package com.example.university.repositories;

import com.example.university.models.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {

    Page<News> findByPublishedTrue(Pageable pageable);
}
