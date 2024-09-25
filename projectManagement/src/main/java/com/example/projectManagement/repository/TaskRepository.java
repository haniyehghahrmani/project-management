package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    @Modifying
    @Query("update TaskEntity oo set oo.deleted=true where oo.id=:id")
    void logicalRemove(Long id);

    List<Task> findTaskByDeletedFalse();

    Optional<Task> findTaskByIdAndDeletedFalse(Long id);

//    List<Task> findTaskByAssignedToAndDeletedFalse(String username);
//
//    List<Task> findTaskByCreateDateAndDeletedFalse(LocalDate createDate);
//
//    List<Task> findTaskByDueDateAndDeletedFalse(LocalDate dueDate);
//
//    List<Task> findTaskByPriorityAndDeletedFalse(String priority);
//
//    List<Task> findTaskByStatusAndDeletedFalse(String status);

    Long countByDeletedFalse();
}
