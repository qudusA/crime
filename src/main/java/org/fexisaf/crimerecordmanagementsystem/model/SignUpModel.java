package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fexisaf.crimerecordmanagementsystem.entity.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpModel {

    @Email(message = "not a valid email address",
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @NotNull(message = "email field cannot be null")
    @NotBlank(message = "email cannot be blank")
    @Column(name= "email", unique = true)
    private String email;

    @NotNull(message = "password is empty")
    @NotBlank(message = "password cannot be blank")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z]).{10,}$",
            message = "password must be at least 10 words," +
                    "must include number, at least 1 upperCase character ")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "first name is required")
    @NotNull(message = "first name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "last name is required")
    @NotNull(message = "last name is required")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "date of birth is required")
    @NotNull(message = "date of birth is required")
    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @NotBlank(message = "phone number is required")
    @NotNull(message = "phone number is required")
    @Pattern(regexp = "^\\+?\\d{8,15}$",message = "invalid phone number")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;

//    @NotContains(forbiddenOccupation = {"judge", "warder", "police"}, adminOnly = true)
//    @NotNull(message = "occupation cannot be null...")
//    @NotBlank(message = "blank")
//    private String occupation;

}
