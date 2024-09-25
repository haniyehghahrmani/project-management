package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Task;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

//    List<Task> findTaskByAssignedToAndDeletedFalse(String username) throws NoContentException;
//
//    List<Task> findTaskByCreateDateAndDeletedFalse(LocalDate createDate) throws NoContentException;
//
//    List<Task> findTaskByDueDateAndDeletedFalse(LocalDate dueDate) throws NoContentException;

    Long countByDeletedFalse();

}
