package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Task;
import com.example.projectManagement.repository.TaskRepository;
import com.example.projectManagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImp implements TaskService {

    private final TaskRepository repository;

    public TaskServiceImp(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public Task save(@Valid Task task) {
        return repository.save(task);
    }

    @Override
    public Task update(Task task) throws NoContentException {
        Task existingTask = repository.findById(task.getId())
                .orElseThrow(
                        () -> new NoContentException("No Active Task Was Found with id " + task.getId() + " To Update!")
                );

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setAssignedTo(task.getAssignedTo());
        existingTask.setCreateDate(task.getCreateDate());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setSubTasks(task.getSubTasks());
        existingTask.setEditing(true);

        return repository.saveAndFlush(existingTask);
    }

    @Override
    public void logicalRemove(Long id) throws NoContentException {
        repository.findTaskByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Task Was Found with id " + id + " To Remove !")
        );
        repository.logicalRemove(id);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Task> findById(Long id) throws NoContentException {
        Optional<Task> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Task Was Found with id : " + id);
        }
    }

    @Override
    public Long getTaskCount() {
        return repository.count();
    }

    @Override
    public Task logicalRemoveWithReturn(Long id) throws NoContentException {
        Task task = repository.findTaskByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Task Was Found with id  " + id + " To Remove !")
        );

        task.setDeleted(true);
        return repository.save(task);
    }

    @Override
    public List<Task> findTaskByDeletedFalse() {
        return repository.findTaskByDeletedFalse();
    }

    @Override
    public Optional<Task> findTaskByIdAndDeletedFalse(Long id) throws NoContentException {
        Optional<Task> optional = repository.findTaskByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Active Task Was Found with id : " + id);
        }
    }

//    @Override
//    public List<Task> findTaskByAssignedToAndDeletedFalse(String username) {
//        return repository.findTaskByAssignedToAndDeletedFalse(username);
//    }
//
//    @Override
//    public List<Task> findTaskByCreateDateAndDeletedFalse(LocalDate createDate) {
//        return repository.findTaskByCreateDateAndDeletedFalse(createDate);
//    }
//
//    @Override
//    public List<Task> findTaskByDueDateAndDeletedFalse(LocalDate dueDate) {
//        return repository.findTaskByDueDateAndDeletedFalse(dueDate);
//    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }
}
