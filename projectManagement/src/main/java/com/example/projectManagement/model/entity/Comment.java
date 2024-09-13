package com.example.projectManagement.model.entity;

import jakarta.persistence.*;
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

@Entity(name = "CommentEntity")
@Table(name = "CommentTbl")
public class Comment {

    @Id
    @SequenceGenerator(name = "commentSeq", sequenceName = "comment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentSeq")
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @OneToOne
    private User author;

    private LocalDate datePosted;

    @ManyToOne
    private Task relatedTask;
}
