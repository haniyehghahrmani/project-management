package com.example.projectManagement.model.entity;

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

@Entity(name = "ReportEntity")
@Table(name = "ReportTbl")
public class Report extends Base{

    @Id
    @SequenceGenerator(name = "reportSeq", sequenceName = "report_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reportSeq")
    @Column(name = "report_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Project project;

    @OneToOne(cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
    private User author;

    @Column(name = "report_generatedAt")
    @FutureOrPresent(message = "Invalid Generated Date")
    @NotNull(message = "Should Not Be Null")
    private LocalDate generatedAt;

    @Transient
    private String faGeneratedAt;

    @Column(name = "report_content",  columnDefinition = "NVARCHAR2(200)")
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,200}$", message = "Invalid Content")
    @Size(min = 3, max = 200, message = "Content must be between 3 and 200 characters")
    @NotBlank(message = "Should Not Be Null")
    private String content;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Task> taskList;

    public String getFaGeneratedAt() {
        return String.valueOf(PersianDate.fromGregorian(generatedAt));
    }

    public void setFaGeneratedAt(String faGeneratedAt) {
        this.generatedAt = PersianDate.parse(faGeneratedAt).toGregorian();
    }
}
