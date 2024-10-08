package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Phase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhaseRepository {

    @Modifying
    @Query("update PhaseEntity oo set oo.deleted=true where oo.id=:id")
    void logicalRemove(Long id);

    List<Phase> findPhaseByDeletedFalse();

    Optional<Phase> findPhaseByIdAndDeletedFalse(Long id);

    List<Phase> findPhaseByPhaseNameAndDeletedFalse(String name);

    Long countByDeletedFalse();
}
