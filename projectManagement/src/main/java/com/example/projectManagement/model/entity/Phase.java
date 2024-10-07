package com.example.projectManagement.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity(name = "PhaseEntity")
@Table(name = "PhaseTbl")
public class Phase {

    @Id
    @SequenceGenerator(name = "phaseSeq", sequenceName = "phase_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phaseSeq")
    @Column(name = "phase_id")
    private Long id;

    @Column(name = "phase_phaseName", columnDefinition = "NVARCHAR2(50)", unique = true)
    @Pattern(regexp = "^[a-zA-Z1-9\\s]{3,30}$", message = "Invalid phaseName")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    @NotBlank(message = "Should Not Be Null")
    private String phaseName;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Project> projectList;
}
