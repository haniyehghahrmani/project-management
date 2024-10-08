package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Phase;
import com.example.projectManagement.repository.PhaseRepository;
import com.example.projectManagement.service.PhaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhaseServiceImpl implements PhaseService {

    private final PhaseRepository repository;

    public PhaseServiceImpl(PhaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Phase save(Phase phase) {
        return repository.save(phase);
    }

    @Override
    public Phase update(Phase phase) throws NoContentException {
        Phase existingPhase = repository.findById(phase.getId())
                .orElseThrow(
                        () -> new NoContentException("No Active Phase Was Found with id " + phase.getId() + " To Update!")
                );

        existingPhase.setPhaseName(phase.getPhaseName());
        existingPhase.setProjectList(phase.getProjectList());
        existingPhase.setEditing(true);

        return repository.saveAndFlush(existingPhase);
    }

    @Override
    public void logicalRemove(Long id) throws NoContentException {
        repository.findPhaseByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Phase Was Found with id " + id + " To Remove !")
        );
        repository.logicalRemove(id);
    }

    @Override
    public List<Phase> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Phase> findById(Long id) throws NoContentException {
        Optional<Phase> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Phase Was Found with id : " + id);
        }
    }

    @Override
    public Long getPhaseCount() {
        return repository.count();
    }

    @Override
    public Phase logicalRemoveWithReturn(Long id) throws NoContentException {
        Phase phase = repository.findPhaseByIdAndDeletedFalse(id).orElseThrow(
                () -> new NoContentException("No Active Phase Was Found with id  " + id + " To Remove !")
        );

        phase.setDeleted(true);
        return repository.save(phase);
    }

    @Override
    public List<Phase> findPhaseByDeletedFalse() {
        return repository.findPhaseByDeletedFalse();
    }

    @Override
    public Optional<Phase> findPhaseByIdAndDeletedFalse(Long id) throws NoContentException {
        Optional<Phase> optional = repository.findPhaseByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            return optional;
        } else {
            throw new NoContentException("No Active Phase Was Found with id : " + id);
        }
    }

    @Override
    public List<Phase> findPhaseByNameAndDeletedFalse(String name) {
        return repository.findPhaseByPhaseNameAndDeletedFalse(name);
    }

    @Override
    public Long countByDeletedFalse() {
        return repository.countByDeletedFalse();
    }

}
