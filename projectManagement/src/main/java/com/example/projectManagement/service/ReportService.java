package com.example.projectManagement.service;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Report;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportService {

    Report save(Report report);

    Report update(Report report) throws NoContentException;

    @Transactional
    void logicalRemove(Long id) throws NoContentException;

    List<Report> findAll();

    Optional<Report> findById(Long id) throws NoContentException;

    Long countByDeletedFalse();

    List<Report> findReportByDeletedFalse();

    Optional<Report> findReportByIdAndDeletedFalse(Long id) throws NoContentException;

//    List<Report> findReportByProjectAndDeletedFalse(Long projectId);
//
//    Optional<Report> findReportByAuthorAndDeletedFalse(String username);

    List<Report> findReportByGeneratedAtAndDeletedFalse(LocalDate generatedAt);
}
