package fall24.swp391.KoiOrderingSystem.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "koi_farm_image")
public class KoiFarmImage {

//    @JoinColumn(name = "id")
//    @ManyToOne
//    private KoiFarms koiFarms;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "koi_farm_id")
    private Long koiFarmId;

    @Column(name = "image_url")
    private String imageUrl;
}
