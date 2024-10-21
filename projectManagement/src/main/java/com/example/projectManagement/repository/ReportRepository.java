package com.example.projectManagement.repository;

import com.example.projectManagement.model.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Modifying
    @Query("update ReportEntity r set r.deleted=true where r.id=:id")
    void logicalRemove(Long id);

    List<Report> findReportByDeletedFalse();

    Optional<Report> findReportByIdAndDeletedFalse(Long id);

//    List<Report> findReportByProjectAndDeletedFalse(Long id);
//
//    Optional<Report> findReportByAuthorAndDeletedFalse(String username);

    List<Report> findReportByGeneratedAtAndDeletedFalse(LocalDate generatedAt);

    Long countByDeletedFalse();
}
