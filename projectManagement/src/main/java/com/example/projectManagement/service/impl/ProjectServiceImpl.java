package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Project;
import com.example.projectManagement.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Override
    public Project save(Project project) {
        return null;
    }

    @Override
    public Project update(Project project) throws NoContentException {
        return null;
    }

    @Override
    public void logicalRemove(Long id) throws NoContentException {

    }

    @Override
    public List<Project> findAll() {
        return null;
    }

    @Override
    public Optional<Project> findById(Long id) throws NoContentException {
        return Optional.empty();
    }

    @Override
    public Long getProjectsCount() {
        return null;
    }

    @Override
    public Project logicalRemoveWithReturn(Long id) throws NoContentException {
        return null;
    }

    @Override
    public List<Project> findProjectByDeletedFalse() {
        return List.of(
                Project.builder().id(1L).name("Tests Project1").build(),
                Project.builder().id(2L).name("Tests Project2").build()
                );
    }

    @Override
    public Optional<Project> findProjectByIdAndDeletedFalse(Long id) throws NoContentException {
        return Optional.empty();
    }

    @Override
    public List<Project> findProjectByNameAndDeletedFalse(String name) {
        return null;
    }

    @Override
    public Long countByDeletedFalse() {
        return null;
    }
}
