package ru.cadmy.blog.service;

import java.util.List;

import ru.cadmy.blog.model.Comment;

/**
 * Created by Cadmy on 29.06.2016.
 */
public interface CommentService {
    void addComment(Comment comment);
    List<Comment> commentListForBlogRecordId(Long id);
}
