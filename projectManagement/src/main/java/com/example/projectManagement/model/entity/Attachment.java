package com.example.projectManagement.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity(name = "AttachmentEntity")
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "attachment_seq")
    @SequenceGenerator(name = "attachment_seq", sequenceName = "attachment_seq", allocationSize = 1)
    private Long id;

    @Column(name = "file_name", length = 50, nullable = false)
    @NotBlank(message = "نام فایل نباید خالی باشد")
    private String fileName;

    @Column(name = "file_type", length = 20)
    private String fileType;

    @Lob
    @Column(name = "content", nullable = false)
    private byte[] content;

    @Column(name = "caption", length = 50, nullable = false)
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,50}$", message = "توضیح فایل معتبر نیست")
    @Size(min = 3, max = 50, message = "توضیح فایل باید بین ۳ تا ۵۰ کاراکتر باشد")
    @NotBlank(message = "توضیح فایل نباید خالی باشد")
    private String caption;
}
