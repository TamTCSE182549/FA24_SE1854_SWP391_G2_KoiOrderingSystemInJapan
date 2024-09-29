package fall24.swp391.KoiOrderingSystem.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "koi_of_farm")
public class KoiOfFarm {

//    @JoinColumn(name = "id")
//    @ManyToOne
//    private KoiFarms koiFarms;
//
//    @JoinColumn(name = "id")
//    @ManyToOne
//    private Kois kois;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "farm_id")
    private Long farmId;

    @Column(name = "koi_id")
    private Long koiId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "available")
    private boolean available;
}
