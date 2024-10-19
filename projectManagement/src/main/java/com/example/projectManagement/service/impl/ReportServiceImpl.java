package com.example.projectManagement.service.impl;

import com.example.projectManagement.exception.NoContentException;
import com.example.projectManagement.model.entity.Report;
import com.example.projectManagement.repository.ReportRepository;
import com.example.projectManagement.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Report save(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public Report update(Report report) throws NoContentException {
        if (!reportRepository.existsById(report.getId())) {
            throw new NoContentException("Report with id " + report.getId() + " not found.");
        }
        return reportRepository.save(report);
    }

    @Override
    @Transactional
    public void logicalRemove(Long id) throws NoContentException {
        if (!reportRepository.existsById(id)) {
            throw new NoContentException("Report with id " + id + " not found.");
        }
        reportRepository.logicalRemove(id);
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findReportByDeletedFalse();
    }

    @Override
    public Optional<Report> findById(Long id) throws NoContentException {
        Optional<Report> report = reportRepository.findReportByIdAndDeletedFalse(id);
        if (report.isEmpty()) {
            throw new NoContentException("Report with id " + id + " not found or deleted.");
        }
        return report;
    }

    @Override
    public Long countByDeletedFalse() {
        return reportRepository.countByDeletedFalse();
    }

    @Override
    public List<Report> findReportByDeletedFalse() {
        return reportRepository.findReportByDeletedFalse();
    }

    @Override
    public Optional<Report> findReportByIdAndDeletedFalse(Long id) throws NoContentException {
        Optional<Report> report = reportRepository.findReportByIdAndDeletedFalse(id);
        if (report.isEmpty()) {
            throw new NoContentException("Report with id " + id + " not found or deleted.");
        }
        return report;
    }

    @Override
    public List<Report> findReportByProjectAndDeletedFalse(Long projectId) {
        return reportRepository.findReportByProjectAndDeletedFalse(projectId);
    }

    @Override
    public Optional<Report> findReportByAuthorAndDeletedFalse(String username) {
        return reportRepository.findReportByAuthorAndDeletedFalse(username);
    }

    @Override
    public List<Report> findReportByGeneratedAtAndDeletedFalse(LocalDate generatedAt) {
        return reportRepository.findReportByGeneratedAtAndDeletedFalse(generatedAt);
    }
}
