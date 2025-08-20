package com.example.projectManagement.model.entity;

import com.example.projectManagement.model.enums.Gender;
import com.github.mfathi91.time.PersianDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

@Entity(name = "PersonEntity")
@Table(name = "persons")
public class Person extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name = "person_seq", sequenceName = "person_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    @Pattern(regexp = "^[آ-یءئa-zA-Z\\s]{3,50}$", message = "نام معتبر نیست")
    @Size(min = 3, max = 50, message = "نام باید بین ۳ تا ۵۰ کاراکتر باشد")
    @NotBlank(message = "نام نباید خالی باشد")
    private String name;

    @Column(name = "lastname", length = 50, nullable = false)
    @Pattern(regexp = "^[آ-یءئa-zA-Z\\s]{3,50}$", message = "نام خانوادگی معتبر نیست")
    @Size(min = 3, max = 50, message = "نام خانوادگی باید بین ۳ تا ۵۰ کاراکتر باشد")
    @NotBlank(message = "نام خانوادگی نباید خالی باشد")
    private String lastname;

    @Column(name = "national_id", length = 10, unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "کد ملی باید ۱۰ رقم باشد")
    @NotBlank(message = "کد ملی نباید خالی باشد")
    private String nationalId;

    @Column(name = "birthdate", nullable = false)
    @Past(message = "تاریخ تولد باید در گذشته باشد")
    @NotNull(message = "تاریخ تولد نباید خالی باشد")
    private LocalDate birthdate;

    @Transient
    private String faBirthdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    @NotNull(message = "جنسیت نباید خالی باشد")
    private Gender gender;

    public String getFaBirthdate() {
        return birthdate != null ? PersianDate.fromGregorian(birthdate).toString() : null;
    }

    public void setFaBirthdate(String faBirthdate) {
        if (faBirthdate != null && !faBirthdate.isEmpty()) {
            this.birthdate = PersianDate.parse(faBirthdate).toGregorian();
        }
    }
}
