package fall24.swp391.KoiOrderingSystem.model.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgotPassRequest {
    @Email(message = "Invalid Email!")
    String email;
}
