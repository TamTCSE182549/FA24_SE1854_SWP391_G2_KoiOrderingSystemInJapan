package fall24.swp391.KoiOrderingSystem.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tour_detail")
public class TourDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tours tour;

    @ManyToOne
    @JoinColumn(name = "farm_id")
    private KoiFarms farm;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    public TourDetail(Tours tour, KoiFarms farm, String description) {
        this.tour = tour;
        this.farm = farm;
        this.description = description;
    }
}
