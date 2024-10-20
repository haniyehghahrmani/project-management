package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Task;
import com.example.projectManagement.model.entity.User;
import com.example.projectManagement.model.enums.Priority;
import com.example.projectManagement.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Modifying
    @Query("update TaskEntity oo set oo.deleted=true where oo.id=:id")
    void logicalRemove(Long id);

    List<Task> findTaskByDeletedFalse();

    Optional<Task> findTaskByIdAndDeletedFalse(Long id);

    List<Task> findTaskByAssignedToAndDeletedFalse(List<User> assignedTo);

    List<Task> findTaskByCreateDateAndDeletedFalse(LocalDateTime createDate);

    List<Task> findTaskByDueDateAndDeletedFalse(LocalDateTime dueDate);

    List<Task> findTaskByPriorityAndDeletedFalse(Priority priority);

    List<Task> findTaskByStatusAndDeletedFalse(Status status);

    Long countByDeletedFalse();
}
