package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Team;
import com.example.projectManagement.repository.TeamRepository;
import com.example.projectManagement.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository repository;

    public TeamServiceImpl(TeamRepository repository) {
        this.repository = repository;
    }

    @Override
    public Team save(Team team) {
        return repository.save(team);
    }

    @Override
    public Team update(Team team) throws NoContentException {
        Team existingTeam = repository.findById(team.getId())
                .orElseThrow(
                        () -> new NoContentException("No Active Team Was Found with id " + team.getId() + " To Update!")
                );

        existingTeam.setTeamName(team.getTeamName());
        existingTeam.setTeamMembers(team.getTeamMembers());
        existingTeam.setEditing(true);

        return repository.saveAndFlush(existingTeam);
    }

    @Override
    public void logicalRemove(Long id) throws NoContentException {
        repository.findTeamByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Team Was Found with id " + id + " To Remove !")
        );
        repository.logicalRemove(id);
    }

    @Override
    public List<Team> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Team> findById(Long id) throws NoContentException {
        Optional<Team> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Team Was Found with id : " + id);
        }
    }

    @Override
    public Long getTeamCount() {
        return repository.count();
    }

    @Override
    public Team logicalRemoveWithReturn(Long id) throws NoContentException {
        Team team = repository.findTeamByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Team Was Found with id  " + id + " To Remove !")
        );

        team.setDeleted(true);
        return repository.save(team);
    }

    @Override
    public List<Team> findTeamByDeletedFalse() {
        return repository.findTeamByDeletedFalse();
    }

    @Override
    public Optional<Team> findTeamByIdAndDeletedFalse(Long id) throws NoContentException {
        Optional<Team> optional = repository.findTeamByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Active Team Was Found with id : " + id);
        }
    }

    @Override
    public List<Team> findTeamByTeamMembersAndDeletedFalse(String username) {
        return repository.findTeamByTeamMembersAndDeletedFalse(username);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }
}
