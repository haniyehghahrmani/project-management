package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Modifying
    @Query("update RoleEntity r set r.deleted=true where r.id=:id")
    void logicalRemove(Long id);

    List<Role> findRoleByDeletedFalse();

    Optional<Role> findRoleByIdAndDeletedFalse(Long id);

    Optional<Role> findRoleByRoleNameAndDeletedFalse(String roleName);

    Long countByDeletedFalse();
}
