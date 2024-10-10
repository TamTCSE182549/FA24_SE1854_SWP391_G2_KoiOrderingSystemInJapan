package fall24.swp391.KoiOrderingSystem.model.request;

import fall24.swp391.KoiOrderingSystem.enums.Gender;
import fall24.swp391.KoiOrderingSystem.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountUpdateRequest {
    @Email(message = "Email not valid")
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String nationality;
    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})",message = "Phone invalid")
    private String phone;
    private String address;
}
