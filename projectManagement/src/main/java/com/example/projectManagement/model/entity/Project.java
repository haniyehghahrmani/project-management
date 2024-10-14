package com.example.projectManagement.model.entity;

import com.example.projectManagement.model.entity.enums.Status;
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

@Entity(name = "ProjectEntity")
@Table(name = "ProjectTbl")
public class Project extends Base{

    @Id
    @SequenceGenerator(name = "projectSeq", sequenceName = "project_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projectSeq")
    @Column(name = "project_id")
    private Long id;

    @Column(name = "project_name",  columnDefinition = "NVARCHAR2(50)")
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,50}$", message = "Invalid Name")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @NotBlank(message = "Should Not Be Null")
    private String name;

    @Column(name = "project_description",  columnDefinition = "NVARCHAR2(200)")
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,200}$", message = "Invalid Description")
    @Size(min = 3, max = 200, message = "Description must be between 3 and 200 characters")
    @NotBlank(message = "Should Not Be Null")
    private String description;

    @Column(name = "project_startDate")
    @PastOrPresent(message = "Invalid Start Date")
    @NotNull(message = "Should Not Be Null")
    private LocalDate startDate;

    @Transient
    private String faStartDate;

    @Column(name = "project_endDate")
    @FutureOrPresent(message = "Invalid End Date")
    @NotNull(message = "Should Not Be Null")
    private LocalDate endDate;

    @Transient
    private String faEndDate;

    @Column(name = "project_status")
    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "Status Should Not Be Null")
    private Status status;

//    @OneToMany(cascade = {CascadeType.MERGE ,CascadeType.PERSIST}, fetch = FetchType.EAGER)
//    public List<User> teamMembers;

    @OneToMany(mappedBy = "project", cascade = {CascadeType.MERGE ,CascadeType.PERSIST}, fetch = FetchType.EAGER)
    public List<Phase> phaseList;

    public String getFaStartDate() {
        return String.valueOf(PersianDate.fromGregorian(startDate));
    }

    public void setFaStartDate(String faStartDate) {
        this.startDate = PersianDate.parse(faStartDate).toGregorian();
    }

    public String getFaEndDate() {
        return String.valueOf(PersianDate.fromGregorian(endDate));
    }

    public void setFaEndDate(String faEndDate) {
        this.endDate = PersianDate.parse(faEndDate).toGregorian();
    }

    public void setUser(List<User> teamMembers) {
    }

    public List<Task> getTask() {
        return null;
    }
}
