package com.example.projectManagement.model.entity;

import com.example.projectManagement.model.entity.enums.Priority;
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

@Entity(name = "TaskEntity")
@Table(name = "TaskTbl")
public class Task {
    @Id
    @SequenceGenerator(name = "taskSeq", sequenceName = "task_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taskSeq")
    @Column(name = "task_id")
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    private User assignedTo;

    private LocalDate dueDate;

    private Priority priority;

    private Status status;

    @OneToMany
    private List<Task> subTasks;

}
