package com.example.projectManagement.model.entity;

import com.example.projectManagement.model.entity.enums.Priority;
import com.example.projectManagement.model.entity.enums.Status;
import com.github.mfathi91.time.PersianDate;
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
public class Task extends Base{

    @Id
    @SequenceGenerator(name = "taskSeq", sequenceName = "task_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "taskSeq")
    @Column(name = "task_id")
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    private User assignedTo;

    private LocalDate createDate;

    @Transient
    private String faCreateDate;

    private LocalDate dueDate;

    @Transient
    private String faDueDate;

    private Priority priority;

    private Status status;

    @OneToMany
    private List<Task> subTasks;

    public String getFaCreateDate() {
        return String.valueOf(PersianDate.fromGregorian(createDate));
    }

    public void setFaCreateDate(String faCreateDate) {
        this.createDate = PersianDate.parse(faCreateDate).toGregorian();
    }

    public String getFaDueDate() {
        return String.valueOf(PersianDate.fromGregorian(dueDate));
    }

    public void setFaDueDate(String faDueDate) {
        this.dueDate = PersianDate.parse(faDueDate).toGregorian();
    }

}
