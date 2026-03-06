package com.example.university.services.contract;

import com.example.university.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Comment createComment(Comment comment);

    Comment findComment(Long id);

    Page<Comment> getAllComments(Pageable pageable);

    Comment updateCommentText(Long id, String text);

    void deleteComment(Long id);
}
