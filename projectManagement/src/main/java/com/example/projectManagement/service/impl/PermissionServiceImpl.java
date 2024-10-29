package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Permission;
import com.example.projectManagement.repository.PermissionRepository;
import com.example.projectManagement.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Permission update(Permission permission) throws NoContentException {
        Permission existingPermission = permissionRepository.findById(permission.getId())
                .orElseThrow(
                        () -> new NoContentException("No Active Permission Was Found with id " + permission.getId() + " To Update!")
                );

        existingPermission.setName(permission.getName());
        existingPermission.setDescription(permission.getDescription());
        existingPermission.setEditing(true);

        return permissionRepository.saveAndFlush(existingPermission);
    }

    @Override
    @Transactional
    public void logicalRemove(Long id) throws NoContentException {
        if (!permissionRepository.existsById(id)) {
            throw new NoContentException("Permission with id " + id + " not found.");
        }
        permissionRepository.logicalRemove(id);
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    @Override
    public Optional<Permission> findById(Long id) throws NoContentException {
        Optional<Permission> permission = permissionRepository.findById(id);
        if (permission.isEmpty()) {
            throw new NoContentException("Permission with id " + id + " not found.");
        }
        return permission;
    }

    @Override
    public Long getPermissionsCount() {
        return permissionRepository.count();
    }

    @Override
    @Transactional
    public Permission logicalRemoveWithReturn(Long id) throws NoContentException {
        Permission permission = permissionRepository.findPermissionByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active permission Was Found with id  " + id + " To Remove !")
        );

        permission.setDeleted(true);
        return permissionRepository.save(permission);
    }

    @Override
    public List<Permission> findPermissionByDeletedFalse() {
        return permissionRepository.findPermissionByDeletedFalse();
    }

    @Override
    public Optional<Permission> findPermissionByIdAndDeletedFalse(Long id) throws NoContentException {
        Optional<Permission> permission = permissionRepository.findPermissionByIdAndDeletedFalse(id);
        if (permission.isEmpty()) {
            throw new NoContentException("Permission with id " + id + " not found or deleted.");
        }
        return permission;
    }

    @Override
    public List<Permission> findPermissionByNameAndDeletedFalse(String name) {
        return permissionRepository.findPermissionByNameAndDeletedFalse(name);
    }

    @Override
    public Long countByDeletedFalse() {
        return permissionRepository.countByDeletedFalse();
    }

    @Override
    public Permission delete(Long id) throws NoContentException {
        Permission permission = findById(id).orElseThrow(() -> new NoContentException("Permission with id " + id + " not found."));
        permissionRepository.delete(permission);
        return permission;
    }

}
