package ua.iv_fr.lukach.marian.in100gram.payload.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse(){
        this.username = "Invalid username";
        this.password = "Invalid password";
    }
}
