package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Comment;
import com.example.projectManagement.repository.CommentRepository;
import com.example.projectManagement.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comment save(@Valid Comment comment) {
        return repository.save(comment);
    }

    @Override
    public Comment update(Comment comment) throws NoContentException {
        Comment existingComment=repository.findById(comment.getId())
                .orElseThrow(
                        () -> new NoContentException("No Active Comment Was Found with id " + comment.getId() + " To Update!")
                );

        existingComment.setContent(comment.getContent());
        existingComment.setAuthor(comment.getAuthor());
        existingComment.setPostedDate(comment.getPostedDate());
        existingComment.setRelatedTask(comment.getRelatedTask());
        existingComment.setEditing(true);

        return repository.saveAndFlush(existingComment);
    }

    @Override
    public void logicalRemove(Long id) throws NoContentException {
        repository.findCommentByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Comment Was Found with id " + id + " To Remove !")
        );
        repository.logicalRemove(id);
    }

    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) throws NoContentException {
        Optional<Comment> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Comment Was Found with id : " + id);
        }
    }

    @Override
    public Long getCommentCount() {
        return repository.count();
    }

    @Override
    public Comment logicalRemoveWithReturn(Long id) throws NoContentException {
        Comment comment = repository.findCommentByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Comment Was Found with id  " + id + " To Remove !")
        );

        comment.setDeleted(true);
        return repository.save(comment);
    }

    @Override
    public List<Comment> findCommentByDeletedFalse() {
        return repository.findCommentByDeletedFalse();
    }

    @Override
    public Optional<Comment> findCommentByIdAndDeletedFalse(Long id) throws NoContentException {
        Optional<Comment> optional = repository.findCommentByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Active Comment Was Found with id : " + id);
        }
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }
}
