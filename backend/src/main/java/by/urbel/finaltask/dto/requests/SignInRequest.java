package by.urbel.finaltask.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignInRequest {
    @NotBlank(message = "Username cannot be empty.")
    @Size(max = 40, message = "Max length of username is 40 characters.")
    private String username;
    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 6, max = 16, message = "The password must be between 6 and 16 characters long.")
    private String password;
}
