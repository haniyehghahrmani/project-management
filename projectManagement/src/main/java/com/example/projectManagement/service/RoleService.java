package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Role;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role save(Role role);

    Role update(Role role) throws NoContentException;

    @Transactional
    void logicalRemove(Long id) throws NoContentException;

    List<Role> findAll();

    Optional<Role> findById(Long id) throws NoContentException;

    Long getRolesCount();

    @Transactional
    Role logicalRemoveWithReturn(Long id) throws NoContentException;

    List<Role> findRoleByDeletedFalse();

    Optional<Role> findRoleByIdAndDeletedFalse(Long id) throws NoContentException;

    Optional<Role> findRoleByRoleNameAndDeletedFalse(String roleName) throws NoContentException;

    Long countByDeletedFalse();
}
