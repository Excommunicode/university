package com.example.university.controllers;

import com.example.university.dto.CommentCreateDto;
import com.example.university.dto.CommentUpdateDto;
import com.example.university.models.Comment;
import com.example.university.services.contract.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<Comment>> getAllComments(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(commentService.getAllComments(pageable));
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
