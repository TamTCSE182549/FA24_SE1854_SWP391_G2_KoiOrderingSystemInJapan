package fall24.swp391.KoiOrderingSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class RegisterRequest {
    @Email(message = "Email not valid")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "password can not blank")
    @Size(min = 6,message = "Password must be at least 6 characters!")
    private String password;

    @Column(length = 50)
    private String fisrtName;

    @Column(length = 50)
    private String lastName;

    @Column()
    private boolean gender;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})",message = "Phone invalid")
    @Column(unique = true)
    private String phone;

    private String address;

}
