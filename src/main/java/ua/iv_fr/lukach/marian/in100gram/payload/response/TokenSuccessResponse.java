package ua.iv_fr.lukach.marian.in100gram.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenSuccessResponse {
    private boolean success;
    private String token;
}
