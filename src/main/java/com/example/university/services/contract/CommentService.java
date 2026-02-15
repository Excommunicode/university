package com.example.university.services.contract;

import com.example.university.models.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Comment comment);

    Comment findComment(Long id);

    List<Comment> getAllComments();

    Comment updateCommentText(Long id, String text);

    void deleteComment(Long id);
}
