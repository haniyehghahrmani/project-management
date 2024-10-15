package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Log;
import com.example.projectManagement.model.enums.LogType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LogService {

    Log save(Log log);

    Log update(Log log) throws NoContentException;

    @Transactional
    void logicalRemove(Long id) throws NoContentException;

    List<Log> findAll();

    Optional<Log> findById(Long id) throws NoContentException;

    Long getLogsCount();

    @Transactional
    Log logicalRemoveWithReturn(Long id) throws NoContentException;

    List<Log> findLogByDeletedFalse();

    Optional<Log> findLogByIdAndDeletedFalse(Long id) throws NoContentException;

    List<Log> findLogByLogTypeAndDeletedFalse(LogType logType);

    Long countByDeletedFalse();

    @Transactional
    Log delete(Long id) throws NoContentException;

    Long deleteById(Long id);
}
