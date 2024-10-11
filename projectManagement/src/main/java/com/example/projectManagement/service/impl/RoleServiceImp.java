package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Role;
import com.example.projectManagement.model.entity.User;
import com.example.projectManagement.repository.RoleRepository;
import com.example.projectManagement.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {

    private final RoleRepository repository;

    public RoleServiceImp(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public Role update(Role role) throws NoContentException {
        Role existingRole = repository.findById(role.getId())
                .orElseThrow(
                        () -> new NoContentException("No Active Role Was Found with id " + role.getId() + " To Update!")
                );

        existingRole.setRoleName(role.getRoleName());
//        existingRole.getUsers().add(role);
        existingRole.setEditing(true);

        return repository.saveAndFlush(existingRole);
    }

    @Override
    public void logicalRemove(Long id) throws NoContentException {
        repository.findRoleByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Role Was Found with id " + id + " To Remove !")
        );
        repository.logicalRemove(id);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) throws NoContentException {
        Optional<Role> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Role Was Found with id : " + id);
        }
    }

    @Override
    public Long getRolesCount() {
        return repository.count();
    }

    @Override
    public Role logicalRemoveWithReturn(Long id) throws NoContentException {
        Role role = repository.findRoleByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Role Was Found with id  " + id + " To Remove !")
        );

        role.setDeleted(true);
        return repository.save(role);
    }

    @Override
    public List<Role> findRoleByDeletedFalse() {
        return repository.findRoleByDeletedFalse();
    }

    @Override
    public Optional<Role> findRoleByIdAndDeletedFalse(Long id) throws NoContentException {
        Optional<Role> optional = repository.findRoleByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Active Role Was Found with id : " + id);
        }
    }

    @Override
    public List<Role> findRoleByRoleNameAndDeletedFalse(String roleName) throws NoContentException {
        return repository.findRoleByRoleNameAndDeletedFalse(roleName);
    }

//    @Override
//    public List<Role> findRoleByUserAndDeletedFalse(User user) throws NoContentException {
//        return repository.findRoleByUserAndDeletedFalse(user);
//    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }

}
