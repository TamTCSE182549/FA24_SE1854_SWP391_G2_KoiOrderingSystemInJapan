package fall24.swp391.KoiOrderingSystem.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "password can not blank")
    @Size(min = 6,message = "Password must be at least 6 characters!")
    String password;

}
