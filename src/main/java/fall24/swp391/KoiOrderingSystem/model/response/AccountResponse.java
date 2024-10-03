package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;

@Data
public class AccountResponse {
    Long id;
    String email;
    String password;
    String token;
}
