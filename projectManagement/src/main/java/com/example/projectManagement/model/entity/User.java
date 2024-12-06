package com.example.projectManagement.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "UserTbl")
public class User extends Base {

    @Id
    @SequenceGenerator(name = "userSeq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_userName", columnDefinition = "NVARCHAR2(50)", unique = true)
    @Pattern(regexp = "^[a-zA-Z1-9\\s]{3,30}$", message = "Invalid userName")
    @Size(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    @NotBlank(message = "Should Not Be Null")
    private String username;

    @Column(name = "user_password", columnDefinition = "NVARCHAR2(50)")
    @Pattern(regexp = "^[a-zA-Z1-9\\s]{8,30}$", message = "Invalid password")
    @Size(min = 8, max = 10, message = "password must be between 8 and 10 characters")
    @NotBlank(message = "Should Not Be Null")
    private String password;

    @Column(name = "user_status")
    private boolean status = true;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_role_id")
    private Role role;

    @ManyToMany(mappedBy = "teamMembers", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference
    private List<Team> teamList;

}
