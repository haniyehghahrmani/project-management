package com.example.projectManagement.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity(name = "PermissionEntity")
@Table(name = "PermissionTbl")
public class Permission extends Base{

    @Id
    @SequenceGenerator(name = "permissionSeq", sequenceName = "permission_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissionSeq")
    @Column(name = "permission_id")
    private Long id;

    @Column(name = "permission_name", columnDefinition = "NVARCHAR2(50)", unique = true)
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,50}$", message = "Invalid Permission Name")
    @Size(min = 3, max = 50, message = "Permission Name must be between 3 and 50 characters")
    @NotBlank(message = "Permission Name should not be null or empty")
    private String name;

    @Column(name = "permission_description", columnDefinition = "NVARCHAR2(255)")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "permissions", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Role> roles;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
