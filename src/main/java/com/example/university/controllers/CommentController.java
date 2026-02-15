package com.example.university.controllers;

import com.example.university.dto.CommentCreateDto;
import com.example.university.dto.CommentUpdateDto;
import com.example.university.models.Comment;
import com.example.university.services.contract.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@Valid @RequestBody CommentCreateDto dto) {
        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setAuthor(dto.getAuthor());
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(comment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> findComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findComment(id));
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Comment> updateCommentText(@PathVariable Long id,
                                                     @Valid @RequestBody CommentUpdateDto dto) {
        return ResponseEntity.ok(commentService.updateCommentText(id, dto.getText()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
