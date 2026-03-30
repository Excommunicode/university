package com.example.university.controllers;

import com.example.university.dto.TodoCreateDto;
import com.example.university.models.Todo;
import com.example.university.services.contract.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Todo> findTodo(@PathVariable String id) {
        return ResponseEntity.ok(todoService.findTodo(id));
    }

    @GetMapping
    public ResponseEntity<Page<Todo>> getAllTodos(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(todoService.getAllTodos(pageable));
    }

    @GetMapping("/completed")
    public ResponseEntity<Page<Todo>> getCompletedTodos(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(todoService.getCompletedTodos(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id,
                                           @Valid @RequestBody TodoCreateDto dto) {
        Todo todo = new Todo();
        todo.setTitle(dto.getTitle());
        todo.setCompleted(dto.getCompleted());
        return ResponseEntity.ok(todoService.updateTodo(id, todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
