package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Team;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TeamService {

    Team save(Team team);

    Team update(Team team) throws NoContentException;

    @Transactional
    void logicalRemove(Long id) throws NoContentException;

    List<Team> findAll();

    Optional<Team> findById(Long id) throws NoContentException;

    Long getTeamCount();

    Team logicalRemoveWithReturn(Long id) throws NoContentException;

    List<Team> findTeamByDeletedFalse();

    Optional<Team> findTeamByIdAndDeletedFalse(Long id) throws NoContentException;

    List<Team> findTeamByTeamMembersAndDeletedFalse(String username);

    Long countByDeletedFalse();
}
