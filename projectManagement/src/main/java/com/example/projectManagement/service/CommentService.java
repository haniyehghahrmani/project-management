package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment save(Comment comment);

    Comment update(Comment comment) throws NoContentException;

    @Transactional
    void logicalRemove(Long id) throws NoContentException;

    List<Comment> findAll();

    Optional<Comment> findById(Long id) throws NoContentException;

    Long getCommentCount();

    Comment logicalRemoveWithReturn(Long id) throws NoContentException;

    List<Comment> findCommentByDeletedFalse();

    Optional<Comment> findCommentByIdAndDeletedFalse(Long id) throws NoContentException;

    Long countByDeletedFalse();
}
