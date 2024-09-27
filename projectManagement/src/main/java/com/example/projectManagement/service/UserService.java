package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.exception.NoUserException;
import com.example.projectManagement.model.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save (User user);

    User update(User user) throws NoContentException;

    @Transactional
    void logicalRemove(Long id) throws NoContentException;

    List<User> findAll();

    Optional<User> findById(Long id) throws NoContentException;

    Long getUsersCount();

    User logicalRemoveWithReturn(Long id) throws NoContentException;

    List<User> findUserByDeletedFalse();

    Optional<User> findUserByIdAndDeletedFalse(Long id) throws NoContentException;

    Optional<User> findUserByUsernameAndDeletedFalse(String username ) throws NoContentException;

    boolean existsUserByUsernameAndDeletedFalse(String username) throws NoUserException;

    Long countByDeletedFalse();
}
