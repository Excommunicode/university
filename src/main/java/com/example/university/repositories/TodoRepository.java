package com.example.university.repositories;

import com.example.university.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TodoRepository extends MongoRepository<Todo, String> {
    Page<Todo> findByCompletedTrue(Pageable pageable);
}
