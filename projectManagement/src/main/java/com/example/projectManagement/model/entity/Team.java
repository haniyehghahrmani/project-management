package com.example.projectManagement.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

@Entity(name = "TeamEntity")
@Table(name = "teams")
public class Team extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq")
    @SequenceGenerator(name = "team_seq", sequenceName = "team_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 50, unique = true, nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9\\s]{3,30}$", message = "نام تیم باید فقط شامل حروف و اعداد باشد")
    @Size(min = 3, max = 30, message = "نام تیم باید بین ۳ تا ۳۰ کاراکتر باشد")
    @NotBlank(message = "نام تیم نباید خالی باشد")
    private String teamName;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "team_user",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonManagedReference
    private List<User> teamMembers;
}
