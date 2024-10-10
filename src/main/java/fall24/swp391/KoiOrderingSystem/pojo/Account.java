package fall24.swp391.KoiOrderingSystem.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import fall24.swp391.KoiOrderingSystem.enums.Gender;
import fall24.swp391.KoiOrderingSystem.enums.Role;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(message = "Email not valid")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "password can not blank")
    @Size(min = 6,message = "Password must be at least 6 characters!")
    private String password;

    @Column(name = "is_active")
    private boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "first_name",length = 50)
    private String firstName;

    @Column(name = "last_name",length = 50)
    private String lastName;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "nationality")
    private String nationality;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})",message = "Phone invalid")
    @Column(name = "phone_number")
    private String phone;

    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate(){
        createdDate = LocalDateTime.now();  // formatted date-time string
        updatedDate = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedDate = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Bookings> bookings;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
