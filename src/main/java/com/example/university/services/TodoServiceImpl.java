package com.example.university.services;

import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.Todo;
import com.example.university.repositories.TodoRepository;
import com.example.university.services.contract.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    @Transactional
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Todo findTodo(String id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
    }

    @Override
    public Page<Todo> getAllTodos(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Todo updateTodo(String id, Todo todo) {
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
        existing.setTitle(todo.getTitle());
        existing.setCompleted(todo.getCompleted());
        return todoRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteTodo(String id) {
        if (!todoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }

    @Override
    public Page<Todo> getCompletedTodos(Pageable pageable) {
        return todoRepository.findByCompletedTrue(pageable);
    }
}
