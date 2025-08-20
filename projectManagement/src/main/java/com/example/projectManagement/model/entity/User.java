package com.example.projectManagement.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity(name = "UserEntity")
@Table(name = "users")
public class User extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "username", length = 30, unique = true, nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{3,30}$", message = "نام کاربری معتبر نیست")
    @Size(min = 3, max = 30, message = "نام کاربری باید بین ۳ تا ۳۰ کاراکتر باشد")
    @NotBlank(message = "نام کاربری نباید خالی باشد")
    private String username;

    @Column(name = "password", length = 60, nullable = false)
    @JsonIgnore
    @Size(min = 8, max = 30, message = "رمز عبور باید بین ۸ تا ۳۰ کاراکتر باشد")
    @NotBlank(message = "رمز عبور نباید خالی باشد")
    private String password;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToMany(mappedBy = "teamMembers", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<Team> teamList;
}
