package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Permission;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    Permission save(Permission permission);

    Permission update(Permission permission) throws NoContentException;

    @Transactional
    void logicalRemove(Long id) throws NoContentException;

    List<Permission> findAll();

    Optional<Permission> findById(Long id) throws NoContentException;

    Long getPermissionsCount();

    Permission logicalRemoveWithReturn(Long id) throws NoContentException;

    List<Permission> findPermissionByDeletedFalse();

    Optional<Permission> findPermissionByIdAndDeletedFalse(Long id) throws NoContentException;

    List<Permission> findPermissionByNameAndDeletedFalse(String name);

    Long countByDeletedFalse();

    Permission delete(Long id);
}
