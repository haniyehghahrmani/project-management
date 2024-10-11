package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Log;
import com.example.projectManagement.model.entity.enums.LogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

    @Modifying
//    @Query("update LogEntity l set l.deleted=true where l.id=:id")
//    void logicalRemove(Long id);

    List<Log> findLogByDeletedFalse();

    Optional<Log> findLogByIdAndDeletedFalse(Long id);

//    List<Log> findLogByLogTypeAndDeletedFalse(LogType logType);

    Long countByDeletedFalse();
}
