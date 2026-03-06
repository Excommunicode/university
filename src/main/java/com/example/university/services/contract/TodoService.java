package com.example.university.services.contract;

import com.example.university.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {

    Todo createTodo(Todo todo);

    Todo findTodo(Long id);

    Page<Todo> getAllTodos(Pageable pageable);

    Todo updateTodo(Long id, Todo todo);

    void deleteTodo(Long id);

    Page<Todo> getCompletedTodos(Pageable pageable);
}
