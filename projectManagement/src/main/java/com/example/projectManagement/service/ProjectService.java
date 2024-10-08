package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Project;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Project save(Project project);

    Project update(Project project) throws NoContentException;

    @Transactional
    void logicalRemove(Long id) throws NoContentException;

    List<Project> findAll();

    Optional<Project> findById(Long id) throws NoContentException;

    Long getProjectsCount();

    Project logicalRemoveWithReturn(Long id) throws NoContentException;

    List<Project> findProjectByDeletedFalse();

    Optional<Project> findProjectByIdAndDeletedFalse(Long id) throws NoContentException;

    List<Project> findProjectByNameAndDeletedFalse(String name);

    Long countByDeletedFalse();
}