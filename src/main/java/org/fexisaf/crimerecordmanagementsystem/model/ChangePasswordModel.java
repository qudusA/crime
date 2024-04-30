package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class ChangePasswordModel {

    @NotBlank(message = "currentPassword field is required.")
    @NotNull(message = "currentPassword field is required.")
    private String currentPassword;

    @NotNull(message = "password is empty")
    @NotBlank(message = "password cannot be blank")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z]).{10,}$",
            message = "password must be at least 10 words," +
                    "must include number, at least 1 upperCase character ")
    @Column(name = "password")
    private String password;

    private String confirmPassword;


}
