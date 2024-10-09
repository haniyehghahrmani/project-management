package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Modifying
    @Query("update TeamEntity oo set oo.deleted=true where oo.id=:id")
    void logicalRemove(Long id);

    List<Team> findTeamByDeletedFalse();

    Optional<Team> findTeamByIdAndDeletedFalse(Long id);

    Long countByDeletedFalse();
}
