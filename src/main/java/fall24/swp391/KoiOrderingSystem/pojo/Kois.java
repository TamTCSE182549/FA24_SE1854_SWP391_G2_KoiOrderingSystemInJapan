package fall24.swp391.KoiOrderingSystem.pojo;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "kois")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kois  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Categories category;

    @Column(name = "koi_name")
    private String koiName;

    @Column(name = "origin")
    private String origin;

    @Column(name = "color")
    private String color;

    @Column(name = "description")
    private String description;

    @Column(name = "koi_image")
    private String koiImage;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "unit_price")
    @Min(value = 0)
    private float unitPrice;

    @OneToMany(mappedBy = "kois", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<KoiOfFarm> koiOfFarmList;

    @OneToMany(mappedBy = "koi", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<BookingKoiDetail> bookingKoiDetails;
}
