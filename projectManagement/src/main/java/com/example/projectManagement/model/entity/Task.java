package com.example.projectManagement.model.entity;

import com.example.projectManagement.model.enums.Priority;
import com.example.projectManagement.model.enums.Status;
import com.github.mfathi91.time.PersianDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Column(name = "task_title",  columnDefinition = "NVARCHAR2(50)")
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,50}$", message = "Invalid Title")
    @Size(min = 3, max = 50, message = "Title must be between 3 and 50 characters")
    @NotBlank(message = "Should Not Be Null")
    private String title;

    @Column(name = "task_description",  columnDefinition = "NVARCHAR2(200)")
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,200}$", message = "Invalid Description")
    @Size(min = 3, max = 200, message = "Description must be between 3 and 200 characters")
    @NotBlank(message = "Should Not Be Null")
    private String description;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<User> assignedTo;

    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Phase> phaseList;

    @Column(name = "task_createDate")
    @PastOrPresent
    private LocalDateTime createDate;

    @Transient
    private String faCreateDate;

    @Column(name = "task_dueDate")
    @Future
    private LocalDateTime dueDate;

    @Transient
    private String faDueDate;

    @Column(name = "task_priority")
    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "Should Not Be Null")
    private Priority priority;

    @Column(name = "task_status")
    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "Should Not Be Null")
    private Status status;

    @OneToMany(cascade = {CascadeType.MERGE ,CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Task> subTasks;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "phase_id")
    private Phase phase;

    //TODO: Check This Method.
    public void addSubTask(Task task){
        if(subTasks == null){
            subTasks = new ArrayList<>();

        }
        subTasks.add(task);

    }

    public String getFaCreateDate() {
        return String.valueOf(PersianDate.fromGregorian(LocalDate.from(createDate)));
    }

    public void setFaCreateDate(String faCreateDate) {
        this.createDate = PersianDate.parse(faCreateDate).toGregorian().atStartOfDay();
    }

    public String getFaDueDate() {
        return String.valueOf(PersianDate.fromGregorian(LocalDate.from(dueDate)));
    }

    public void setFaDueDate(String faDueDate) {
        this.dueDate = PersianDate.parse(faDueDate).toGregorian().atStartOfDay();
    }
}
