package com.example.projectManagement.model.entity;

import com.example.projectManagement.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.github.mfathi91.time.PersianDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

@Entity
@Table(name = "projects")
public class Project extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @SequenceGenerator(name = "project_seq", sequenceName = "project_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    @Pattern(regexp = "^[آ-یa-zA-Z\\s]{3,50}$", message = "نام پروژه معتبر نیست")
    @Size(min = 3, max = 50, message = "نام پروژه باید بین ۳ تا ۵۰ کاراکتر باشد")
    @NotBlank(message = "نام پروژه نباید خالی باشد")
    private String name;

    @Column(name = "description", length = 200, nullable = false)
    @Pattern(regexp = "^[آ-یa-zA-Z\\s]{3,200}$", message = "توضیحات معتبر نیست")
    @Size(min = 3, max = 200, message = "توضیحات باید بین ۳ تا ۲۰۰ کاراکتر باشد")
    @NotBlank(message = "توضیحات نباید خالی باشد")
    private String description;

    @Column(name = "start_date", nullable = false)
    @PastOrPresent(message = "تاریخ شروع باید در گذشته یا حال باشد")
    @NotNull(message = "تاریخ شروع نباید خالی باشد")
    private LocalDate startDate;

    @Transient
    private String faStartDate;

    @Column(name = "end_date", nullable = false)
    @FutureOrPresent(message = "تاریخ پایان باید در آینده یا حال باشد")
    @NotNull(message = "تاریخ پایان نباید خالی باشد")
    private LocalDate endDate;

    @Transient
    private String faEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Team> teamList;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Task> taskList;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Phase> phaseList;

    public String getFaStartDate() {
        try {
            return startDate != null ? PersianDate.fromGregorian(startDate).toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void setFaStartDate(String faStartDate) {
        try {
            if (faStartDate != null && !faStartDate.isEmpty()) {
                this.startDate = PersianDate.parse(faStartDate).toGregorian();
            }
        } catch (Exception ignored) {
        }
    }

    public String getFaEndDate() {
        try {
            return endDate != null ? PersianDate.fromGregorian(endDate).toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void setFaEndDate(String faEndDate) {
        try {
            if (faEndDate != null && !faEndDate.isEmpty()) {
                this.endDate = PersianDate.parse(faEndDate).toGregorian();
            }
        } catch (Exception ignored) {
        }
    }
}
