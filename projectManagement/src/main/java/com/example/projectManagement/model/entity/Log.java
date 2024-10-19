package com.example.projectManagement.model.entity;

import com.example.projectManagement.model.enums.LogType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity(name = "LogEntity")
@Table(name = "LogTbl")
public class Log extends Base {

    @Id
    @SequenceGenerator(name = "logSeq", sequenceName = "log_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logSeq")
    @Column(name = "log_id")
    private Long id;

    @Column(name = "log_message", columnDefinition = "NVARCHAR2(255)")
    @NotBlank(message = "Log message should not be null or empty")
    private String message;

    @Column(name = "log_timestamp", updatable = false)
    @NotNull(message = "Timestamp should not be null")
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "log_type", columnDefinition = "NVARCHAR2(50)")
    @NotNull(message = "Log type should not be null")
    private LogType logType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User should not be null")
    private User user;

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
}
