package com.example.university.services;

import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.Comment;
import com.example.university.repositories.CommentRepository;
import com.example.university.services.contract.CommentService;
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
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public Comment createComment(Comment comment) {
        log.info("createComment called: textLength={}", comment.getText() != null ? comment.getText().length() : 0);
        return commentRepository.save(comment);
    }

    @Override
    public Comment findComment(Long id) {
        log.info("findComment called: id={}", id);
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
    }

    @Override
    public Page<Comment> getAllComments(Pageable pageable) {
        log.info("getAllComments called: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return commentRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Comment updateCommentText(Long id, String text) {
        log.info("updateCommentText called: id={}, textLength={}", id, text != null ? text.length() : 0);
        Comment existing = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
        existing.setText(text);
        return commentRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        log.info("deleteComment called: id={}", id);
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }
}
