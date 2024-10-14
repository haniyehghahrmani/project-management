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

@Entity(name = "CommentEntity")
@Table(name = "CommentTbl")
public class Comment extends Base{

    @Id
    @SequenceGenerator(name = "commentSeq", sequenceName = "comment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentSeq")
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_content",  columnDefinition = "NVARCHAR2(200)")
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,200}$", message = "Invalid Content")
    @Size(min = 3, max = 200, message = "Content must be between 3 and 200 characters")
    @NotBlank(message = "Content Should Not Be Null")
    private String content;

    @OneToMany(cascade = {CascadeType.MERGE ,CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<User> author;

    @Column(name = "comment_postedDate")
    @Past(message = "Invalid Posted Date")
    @NotNull(message = "Posted Date Should Not Be Null")
    private LocalDate postedDate;

    @Transient
    private String faPostedDate;

    @OneToMany(cascade = {CascadeType.MERGE ,CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<Task> relatedTask;

    public String getFaPostedDate() {
        return String.valueOf(PersianDate.fromGregorian(postedDate));
    }

    public void setFaPostedDate(String faPostedDate) {
        this.postedDate = PersianDate.parse(faPostedDate).toGregorian();
    }
}
