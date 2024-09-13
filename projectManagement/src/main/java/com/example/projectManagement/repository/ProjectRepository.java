package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProjectRepository extends JpaRepository<Project,Long>{

    @Modifying
    @Query("update ProjectEntity oo set oo.deleted=true where oo.id=:id")
    void logicalRemove(Long id);

    List<Project> findProjectByDeletedFalse();

    Optional<Project> findProjectByIdAndDeletedFalse(Long id);

    List<Project> findProjectByNameAndDeletedFalse(String name);

    Long countByDeletedFalse();
}
