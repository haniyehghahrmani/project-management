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

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity(name = "RoleEntity")
@Table(name = "roles")
public class Role extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    private Long id;

    @Column(name = "role_name", length = 50, nullable = false)
    @Pattern(regexp = "^[a-zA-Zآ-ی\\s]{3,50}$", message = "نام نقش معتبر نیست")
    @Size(min = 3, max = 50, message = "نام نقش باید بین ۳ تا ۵۰ کاراکتر باشد")
    @NotBlank(message = "نام نقش نباید خالی باشد")
    private String roleName;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions;

    // اگر بخوای ارتباط با User رو فعال کنی:
    // @OneToMany(mappedBy = "role", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    // private List<User> users;
}
