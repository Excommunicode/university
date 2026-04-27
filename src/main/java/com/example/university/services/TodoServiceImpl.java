package com.example.university.services;

import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.Todo;
import com.example.university.repositories.TodoRepository;
import com.example.university.services.contract.TodoService;
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
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    @Transactional
    public Todo createTodo(Todo todo) {
        log.info("createTodo called: title='{}'", todo.getTitle());
        return todoRepository.save(todo);
    }

    @Override
    public Todo findTodo(String id) {
        log.info("findTodo called: id={}", id);
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
    }

    @Override
    public Page<Todo> getAllTodos(Pageable pageable) {
        log.info("getAllTodos called: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return todoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Todo updateTodo(String id, Todo todo) {
        log.info("updateTodo called: id={}, title='{}'", id, todo.getTitle());
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        existing.setTitle(todo.getTitle());
        existing.setCompleted(todo.getCompleted());
        return todoRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteTodo(String id) {
        log.info("deleteTodo called: id={}", id);
        if (!todoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }

    @Override
    public Page<Todo> getCompletedTodos(Pageable pageable) {
        log.info("getCompletedTodos called: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return todoRepository.findByCompletedTrue(pageable);
    }
}
