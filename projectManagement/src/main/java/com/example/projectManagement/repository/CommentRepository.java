package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Modifying
    @Query("update CommentEntity oo set oo.deleted=true where oo.id=:id")
    void logicalRemove(Long id);

    List<Comment> findCommentByDeletedFalse();

    Optional<Comment> findCommentByIdAndDeletedFalse(Long id);

    Long countByDeletedFalse();
}
