package com.example.university.controllers;

import com.example.university.dto.TodoCreateDto;
import com.example.university.models.Todo;
import com.example.university.services.contract.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody TodoCreateDto dto) {
        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setCompleted(dto.getCompleted() != null ? dto.getCompleted() : false);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoService.createTodo(todo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> findTodo(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.findTodo(id));
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Todo>> getCompletedTodos() {
        return ResponseEntity.ok(todoService.getCompletedTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id,
                                           @Valid @RequestBody TodoCreateDto dto) {
        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setCompleted(dto.getCompleted());
        return ResponseEntity.ok(todoService.updateTodo(id, todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
