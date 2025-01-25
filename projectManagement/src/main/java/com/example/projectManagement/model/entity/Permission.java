package com.example.projectManagement.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "permissions")
@EntityListeners(AuditingEntityListener.class)
public class Permission extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_seq")
    @SequenceGenerator(name = "permission_seq", sequenceName = "permission_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 50, unique = true, nullable = false)
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,50}$", message = "Invalid permission name")
    @Size(min = 3, max = 50, message = "Permission name must be between 3 and 50 characters")
    @NotBlank(message = "Permission name should not be blank")
    private String name;

    @Column(name = "description", length = 255)
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

}
