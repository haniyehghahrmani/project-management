package com.example.projectManagement.model.entity;

import com.example.projectManagement.model.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity(name = "ProjectEntity")
@Table(name = "ProjectTbl")
public class Project {

    @Id
    @SequenceGenerator(name = "projectSeq", sequenceName = "project_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projectSeq")
    @Column(name = "project_id")
    private Long id;

    private String name;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private Status status;

    @OneToMany
    private List<User> teamMembers;

    @OneToMany
    private List<Task> tasks;

}
