package fall24.swp391.KoiOrderingSystem.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "checkin")
public class Checkin extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name",length = 50)
    private String firstName;

    @Column(name = "last_name",length = 50)
    private String lastName;

    @Pattern(regexp = "",message = "Passport invalid")
    @Column(name = "passport_number",length = 20)
    private String passportNumber;

    private String nationality;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "checkin_date")
    private Date checkinDate;

    private String airline;

    private String airport;

    private String status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "booking_id") //name same foreign key mapping
    private Bookings booking;

}
