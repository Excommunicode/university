package com.example.university.services.contract;

import com.example.university.models.Todo;

import java.util.List;

public interface TodoService {

    Todo createTodo(Todo todo);

    Todo findTodo(Long id);

    List<Todo> getAllTodos();

    Todo updateTodo(Long id, Todo todo);

    void deleteTodo(Long id);

    List<Todo> getCompletedTodos();
}
