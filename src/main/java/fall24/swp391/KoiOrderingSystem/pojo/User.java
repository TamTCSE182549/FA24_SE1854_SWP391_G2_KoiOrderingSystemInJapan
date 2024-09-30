package fall24.swp391.KoiOrderingSystem.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
public class User {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",length = 50)
    private String fisrtName;

    @Column(name = "last_name",length = 50)
    private String lastName;

    @Column(name = "gender")
    private boolean gender;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})",message = "Phone invalid")
    @Column(name = "phone_number",unique = true)
    private String phone;

    private String address;

    @OneToOne(cascade = CascadeType.ALL )
    @JoinColumn(name = "acc_id")
    private Account account;

    @Column(name = "created_at")
    private LocalDateTime createdDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate(){
        createdDate = LocalDateTime.parse(LocalDateTime.now().format(formatter));  // formatted date-time string
        updatedDate = LocalDateTime.parse(LocalDateTime.now().format(formatter));
    }
    @PreUpdate
    protected void onUpdate(){
        updatedDate = LocalDateTime.parse(LocalDateTime.now().format(formatter));
    }
}
