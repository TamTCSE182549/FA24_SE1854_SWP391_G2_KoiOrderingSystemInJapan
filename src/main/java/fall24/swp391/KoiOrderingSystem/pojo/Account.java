package fall24.swp391.KoiOrderingSystem.pojo;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(message = "Email not valid")
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "(84|0[35789])+(\\d{8})", message = "Phone invalid")
    @Column(name = "phone_number",unique = true)
    private String phone;

    @NotBlank(message = "password can not blank")
    @Size(min = 6,message = "Password must be at least 6 characters!")
    private String password;

    @Column(name = "is_active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
