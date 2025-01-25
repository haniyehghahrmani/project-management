package com.example.projectManagement.model.entity;

import com.example.projectManagement.model.enums.Priority;
import com.example.projectManagement.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.mfathi91.time.PersianDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "tasks")
public class Task extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_seq", allocationSize = 1)
    private Long id;

    @Column(name = "title", length = 50, nullable = false)
    @Pattern(regexp = "^[آ-یa-zA-Z0-9\\s.,،]{3,50}$", message = "عنوان معتبر نیست")
    @Size(min = 3, max = 50, message = "عنوان باید بین ۳ تا ۵۰ کاراکتر باشد")
    @NotBlank(message = "عنوان نباید خالی باشد")
    private String title;

    @Column(name = "description", length = 200, nullable = false)
    @Pattern(regexp = "^[آ-یa-zA-Z0-9\\s.,،]{3,200}$", message = "توضیحات معتبر نیست")
    @Size(min = 3, max = 200, message = "توضیحات باید بین ۳ تا ۲۰۰ کاراکتر باشد")
    @NotBlank(message = "توضیحات نباید خالی باشد")
    private String description;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> assignedTo;

    @Column(name = "create_date")
    @PastOrPresent
    private LocalDateTime createDate;

    @Transient
    private String faCreateDate;

    @Column(name = "due_date")
    @Future
    private LocalDateTime dueDate;

    @Transient
    private String faDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "parent_task_id")
    private List<Task> subTasks;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "phase_id")
    @JsonManagedReference
    private Phase phase;

    public void addSubTask(Task task) {
        if (subTasks == null) subTasks = new ArrayList<>();
        subTasks.add(task);
    }

    public String getFaCreateDate() {
        try {
            return createDate != null ? PersianDate.fromGregorian(createDate.toLocalDate()).toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void setFaCreateDate(String faCreateDate) {
        try {
            this.createDate = PersianDate.parse(faCreateDate).toGregorian().atStartOfDay();
        } catch (Exception ignored) {
        }
    }

    public String getFaDueDate() {
        try {
            return dueDate != null ? PersianDate.fromGregorian(dueDate.toLocalDate()).toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void setFaDueDate(String faDueDate) {
        try {
            this.dueDate = PersianDate.parse(faDueDate).toGregorian().atStartOfDay();
        } catch (Exception ignored) {
        }
    }
}
