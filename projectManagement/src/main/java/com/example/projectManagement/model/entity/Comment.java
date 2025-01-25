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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "comments")
public class Comment extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq", sequenceName = "comment_seq", allocationSize = 1)
    private Long id;

    @Column(name = "content", length = 200, nullable = false)
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,200}$", message = "متن کامنت معتبر نیست")
    @Size(min = 3, max = 200, message = "متن کامنت باید بین ۳ تا ۲۰۰ کاراکتر باشد")
    @NotBlank(message = "متن کامنت نباید خالی باشد")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "posted_date", nullable = false)
    @Past(message = "تاریخ ارسال باید در گذشته باشد")
    @NotNull(message = "تاریخ ارسال نباید خالی باشد")
    private LocalDate postedDate;

    @Transient
    private String faPostedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task relatedTask;

    public String getFaPostedDate() {
        try {
            return postedDate != null ? PersianDate.fromGregorian(postedDate).toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void setFaPostedDate(String faPostedDate) {
        try {
            if (faPostedDate != null && !faPostedDate.isEmpty()) {
                this.postedDate = PersianDate.parse(faPostedDate).toGregorian();
            }
        } catch (Exception ignored) {
        }
    }
}
