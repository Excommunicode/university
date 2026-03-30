package com.example.university.services.contract;

import com.example.university.models.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {

    Todo createTodo(Todo todo);

    Todo findTodo(String id);

    Page<Todo> getAllTodos(Pageable pageable);

    Todo updateTodo(String id, Todo todo);

    void deleteTodo(String id);

    Page<Todo> getCompletedTodos(Pageable pageable);
}
