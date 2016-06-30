package ru.cadmy.blog.service;

import java.util.List;

import ru.cadmy.blog.model.Comment;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> commentListForBlogRecordId(Long id);
}
