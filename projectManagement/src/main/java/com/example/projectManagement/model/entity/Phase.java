package com.example.projectManagement.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

@Entity
@Table(name = "phases")
public class Phase extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phase_seq")
    @SequenceGenerator(name = "phase_seq", sequenceName = "phase_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 50, unique = true, nullable = false)
    @Pattern(regexp = "^[آ-یa-zA-Z0-9\\s]{3,30}$", message = "نام فاز معتبر نیست")
    @Size(min = 3, max = 30, message = "نام فاز باید بین ۳ تا ۳۰ کاراکتر باشد")
    @NotBlank(message = "نام فاز نباید خالی باشد")
    private String phaseName;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonManagedReference
    private Project project;

    @OneToMany(mappedBy = "phase", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<Task> taskList;
}
