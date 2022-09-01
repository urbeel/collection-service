package by.urbel.finaltask.dto.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {
    @NotBlank(message = "Username cannot be empty.")
    @Size(max = 40, message = "Max length of username is 40 characters.")
    private String username;

    @Email(message = "Incorrect email.")
    @NotBlank(message = "Email cannot be empty.")
    @Size(max = 50, message = "Max length of email is 50 characters.")
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long.")
    private String password;
}
