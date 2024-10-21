package fall24.swp391.KoiOrderingSystem.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "koi_farm_detail")
public class KoiFarmDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private KoiFarms farm;

    @Column(name = "description")
    private String description;

    public KoiFarmDetail(KoiFarms farm, String description) {
        this.farm = farm;
        this.description = description;
    }
}
