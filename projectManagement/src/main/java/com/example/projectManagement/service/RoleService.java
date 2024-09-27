package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Role;
import com.example.projectManagement.model.entity.User;
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

    Role logicalRemoveWithReturn(Long id) throws NoContentException;

    List<Role> findRoleByDeletedFalse();

    Optional<Role> findRoleByIdAndDeletedFalse(Long id) throws NoContentException;

    List<Role> findRoleByRoleNameAndDeletedFalse(String roleName) throws NoContentException;

    List<Role> findRoleByUserAndDeletedFalse(User user) throws NoContentException;

    Long countByDeletedFalse();
}
