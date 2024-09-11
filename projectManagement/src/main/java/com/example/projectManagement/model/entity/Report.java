package com.example.projectManagement.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity(name = "ReportEntity")
@Table(name = "ReportTbl")
public class Report {
    @Id
    @SequenceGenerator(name = "reportSeq", sequenceName = "report_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportSeq")
    @Column(name = "report_id")
    private Long id;

    @ManyToOne
    private Project project;

    @OneToOne
    private User author;

    private LocalDate generatedAt;

    private String content;
}
