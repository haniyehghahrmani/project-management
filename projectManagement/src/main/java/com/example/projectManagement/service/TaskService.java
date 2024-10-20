package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Task;
import com.example.projectManagement.model.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    Task save(Task task);

    Task update(Task task) throws NoContentException;

    @Transactional
    void logicalRemove(Long id) throws NoContentException;

    List<Task> findAll();

    Optional<Task> findById(Long id) throws NoContentException;

    Long getTaskCount();

    Task logicalRemoveWithReturn(Long id) throws NoContentException;

    List<Task> findTaskByDeletedFalse();

    Optional<Task> findTaskByIdAndDeletedFalse(Long id) throws NoContentException;

    List<Task> findTaskByAssignedToAndDeletedFalse(List<User> username) throws NoContentException;

    List<Task> findTaskByCreateDateAndDeletedFalse(LocalDateTime createDate) throws NoContentException;

    List<Task> findTaskByDueDateAndDeletedFalse(LocalDateTime dueDate) throws NoContentException;

    List<Task> findTaskByPriorityAndDeletedFalse(String priority) throws NoContentException;

    List<Task> findTaskByStatusAndDeletedFalse(String status) throws NoContentException;

    Long countByDeletedFalse();
}
