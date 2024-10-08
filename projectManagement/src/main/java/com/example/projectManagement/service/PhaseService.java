package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Phase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PhaseService {

    Phase save(Phase phase);

    Phase update(Phase phase) throws NoContentException;

    @Transactional
    void logicalRemove(Long id) throws NoContentException;

    List<Phase> findAll();

    Optional<Phase> findById(Long id) throws NoContentException;

    Long getPhaseCount();

    Phase logicalRemoveWithReturn(Long id) throws NoContentException;

    List<Phase> findPhaseByDeletedFalse();

    Optional<Phase> findPhaseByIdAndDeletedFalse(Long id) throws NoContentException;

    List<Phase> findPhaseByNameAndDeletedFalse(String name);

    Long countByDeletedFalse();
}
