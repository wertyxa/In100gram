package ua.iv_fr.lukach.marian.in100gram.payload.request;

import lombok.Data;
import ua.iv_fr.lukach.marian.in100gram.annotations.PasswordMatches;
import ua.iv_fr.lukach.marian.in100gram.annotations.ValidEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
public class SignupRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please enter you First Name")
    private String firstName;
    @NotEmpty(message = "Please enter you Last Name")
    private String lastName;
    @NotEmpty(message = "Please enter you Username")
    private String username;
    @NotEmpty(message = "Please enter you Password")
    @Size(min = 6)
    private String password;
    private String confirmPassword;

}
