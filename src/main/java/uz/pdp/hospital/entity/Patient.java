package uz.pdp.hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "First name cannot be null")
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "First name must start with a capital letter and contain only letters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Last name must start with a capital letter and contain only letters")
    private String lastName;

    @NotNull(message = "Age cannot be null")
    @Positive(message = "Age must be a positive number")
    private Integer age;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^9989[0-9]{8}$", message = "Write correct number")
    private String phoneNumber;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    private LocalDateTime checkedDate;


    @ManyToOne
    private Doctor doctor;

    public Patient(String firstName, String lastName, Integer age, String phoneNumber, Status status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }
}
