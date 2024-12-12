package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Project;
import com.example.projectManagement.repository.ProjectRepository;
import com.example.projectManagement.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public Project save(Project project) {
        return repository.save(project);
    }

    @Override
    public Project update(Project project) throws NoContentException {
        Project existingProject = repository.findById(project.getId())
                .orElseThrow(() -> new NoContentException("No Project Was Found with id " + project.getId() + " To Update!"));

        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setEndDate(project.getEndDate());
        existingProject.setStatus(project.getStatus());
        existingProject.setPhaseList(project.getPhaseList());
        existingProject.setEditing(true);

        return repository.saveAndFlush(existingProject);
    }

    @Override
    @Transactional
    public void logicalRemove(Long id) throws NoContentException {
        Project project = repository.findProjectByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NoContentException("No Active Project Was Found with id " + id + " To Remove!"));

        repository.logicalRemove(id);
    }

    @Override
    public List<Project> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Project> findById(Long id) throws NoContentException {
        return repository.findById(id)
                .or(() -> {
                    try {
                        throw new NoContentException("No Project Was Found with id: " + id);
                    } catch (NoContentException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public Long getProjectsCount() {
        return repository.count();
    }

    @Override
    public Project logicalRemoveWithReturn(Long id) throws NoContentException {
        Project project = repository.findProjectByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NoContentException("No Active Project Was Found with id " + id + " To Remove!"));

        project.setDeleted(true);
        return repository.save(project);
    }

    @Override
    public List<Project> findProjectByDeletedFalse() {
        return repository.findProjectByDeletedFalse();
    }

    @Override
    public Optional<Project> findProjectByIdAndDeletedFalse(Long id) throws NoContentException {
        return repository.findProjectByIdAndDeletedFalse(id)
                .or(() -> {
                    try {
                        throw new NoContentException("No Active Project Was Found with id: " + id);
                    } catch (NoContentException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public List<Project> findProjectByNameAndDeletedFalse(String name) {
        return repository.findProjectByNameAndDeletedFalse(name);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }
}
