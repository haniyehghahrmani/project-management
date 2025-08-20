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
@Table(name = "reports")
public class Report extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq")
    @SequenceGenerator(name = "report_seq", sequenceName = "report_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "generated_at", nullable = false)
    @FutureOrPresent(message = "تاریخ تولید باید امروز یا آینده باشد")
    @NotNull(message = "تاریخ تولید نباید خالی باشد")
    private LocalDate generatedAt;

    @Transient
    private String faGeneratedAt;

    @Column(name = "content", length = 200, nullable = false)
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,200}$", message = "متن گزارش معتبر نیست")
    @Size(min = 3, max = 200, message = "متن گزارش باید بین ۳ تا ۲۰۰ کاراکتر باشد")
    @NotBlank(message = "متن گزارش نباید خالی باشد")
    private String content;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Task> taskList;

    public String getFaGeneratedAt() {
        try {
            return generatedAt != null ? PersianDate.fromGregorian(generatedAt).toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void setFaGeneratedAt(String faGeneratedAt) {
        try {
            if (faGeneratedAt != null && !faGeneratedAt.isEmpty()) {
                this.generatedAt = PersianDate.parse(faGeneratedAt).toGregorian();
            }
        } catch (Exception ignored) {
        }
    }
}
