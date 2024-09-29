package fall24.swp391.KoiOrderingSystem.pojo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "koi_farms")
public class KoiFarms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "farmName")
    private String farmName;

    @Column(name = "farmPhoneNumber")
    private String farmPhoneNumber;

    @Column(name = "farmEmail")
    private String farmEmail;

    @Column(name = "farmAddress")
    private String farmAddress;

    @Column(name = "farmImage")
    private String farmImage;

    @Column(name = "website")
    private String website;

    @Column(name = "isActive")
    private boolean isActive;
}
