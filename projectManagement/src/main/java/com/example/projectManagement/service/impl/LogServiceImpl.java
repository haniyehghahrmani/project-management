package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Log;
import com.example.projectManagement.model.entity.enums.LogType;
import com.example.projectManagement.repository.LogRepository;
import com.example.projectManagement.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }

    @Override
    public Log update(Log log) throws NoContentException {
        if (!logRepository.existsById(log.getId())) {
            throw new NoContentException("Log with id " + log.getId() + " not found.");
        }
        return logRepository.save(log);
    }

    @Override
    @Transactional
    public void logicalRemove(Long id) throws NoContentException {
        if (!logRepository.existsById(id)) {
            throw new NoContentException("Log with id " + id + " not found.");
        }
//        logRepository.logicalRemove(id);
    }

    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }

    @Override
    public Optional<Log> findById(Long id) throws NoContentException {
        Optional<Log> log = logRepository.findById(id);
        if (log.isEmpty()) {
            throw new NoContentException("Log with id " + id + " not found.");
        }
        return log;
    }

    @Override
    public Long getLogsCount() {
        return logRepository.count();
    }

    @Override
    @Transactional
    public Log logicalRemoveWithReturn(Long id) throws NoContentException {
        if (!logRepository.existsById(id)) {
            throw new NoContentException("Log with id " + id + " not found.");
        }
//        logRepository.logicalRemove(id);
        return logRepository.findById(id).orElseThrow(() -> new NoContentException("Log with id " + id + " not found."));
    }

    @Override
    public List<Log> findLogByDeletedFalse() {
        return logRepository.findLogByDeletedFalse();
    }

    @Override
    public Optional<Log> findLogByIdAndDeletedFalse(Long id) throws NoContentException {
        Optional<Log> log = logRepository.findLogByIdAndDeletedFalse(id);
        if (log.isEmpty()) {
            throw new NoContentException("Log with id " + id + " not found or deleted.");
        }
        return log;
    }

//    @Override
//    public List<Log> findLogByLogTypeAndDeletedFalse(LogType logType) {
//        return logRepository.findLogByLogTypeAndDeletedFalse(logType);
//    }

    @Override
    public Long countByDeletedFalse() {
        return logRepository.countByDeletedFalse();
    }

    @Override
    @Transactional
    public Log delete(Long id) throws NoContentException {
        Log log = logRepository.findById(id).orElseThrow(() -> new NoContentException("Log with id " + id + " not found."));
        logRepository.delete(log);
        return log;
    }
}
