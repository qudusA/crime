package org.fexisaf.crimerecordmanagementsystem.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class ChangePasswordModel {

    @NotBlank(message = "currentPassword field is required.")
    @NotNull(message = "currentPassword field is required.")
    private String currentPassword;

    @NotNull(message = "password field is required")
    @NotBlank(message = "password field is required")
    @Pattern(regexp = "^\\+?\\d{8,15}$", message = "password must be at least 10 words," +
            "must include number, at least 1 upperCase character ")
    private String password;

    private String confirmPassword;

    @Email(message = "not a valid email.")
    private String email;

    public ChangePasswordModel(String password, String confirmPassword,
                               String email, String currentPassword){

        if(email != null) {
            if (password.equals(confirmPassword)) {
                this.email = email.trim();
                this.password = password.trim();
                this.confirmPassword = confirmPassword.trim();
            }
        }
        if(currentPassword != null) {
            if (password.equals(confirmPassword)) {
                this.currentPassword = currentPassword.trim();
                this.password = password.trim();
                this.confirmPassword = confirmPassword.trim();
            }
        }
    }
}
