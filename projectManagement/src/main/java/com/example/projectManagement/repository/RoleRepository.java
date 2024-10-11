package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Role;
import com.example.projectManagement.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Modifying
    @Query("update roleEntity oo set oo.deleted=true where oo.id=:id")
    void logicalRemove(Long id);

    List<Role> findRoleByDeletedFalse();

    Optional<Role> findRoleByIdAndDeletedFalse(Long id);

    List<Role> findRoleByRoleNameAndDeletedFalse(String roleName);

//    List<Role> findRoleByUserAndDeletedFalse(User user);

    Long countByDeletedFalse();
}
