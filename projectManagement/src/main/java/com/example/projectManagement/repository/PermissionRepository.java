package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Modifying
    @Query("update PermissionEntity p set p.deleted=true where p.id=:id")
    void logicalRemove(Long id);

    List<Permission> findPermissionByDeletedFalse();

    Optional<Permission> findPermissionByIdAndDeletedFalse(Long id);

    List<Permission> findPermissionByNameAndDeletedFalse(String name);

    Long countByDeletedFalse();
}
