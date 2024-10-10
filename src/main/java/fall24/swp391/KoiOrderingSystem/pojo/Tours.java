package fall24.swp391.KoiOrderingSystem.pojo;

import fall24.swp391.KoiOrderingSystem.enums.TourStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tours")
public class Tours{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tour_name")
    private String tourName;

    @Column(name = "unit_price")
    @Min(value = 0)
    private float unitPrice;

    @Column(name = "max_participants")
    @Min(value = 1)
    private int maxParticipants;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "tour_image")
    private String tourImg;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TourStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "created_by")
    private Account createdBy;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "updated_by")
    private Account updatedBy;

    @PrePersist
    protected void onCreate(){
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate(){
        updatedDate = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "tourId", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<BookingTourDetail> bookingTourDetails;

    @OneToMany(mappedBy = "tour", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<TourDetail> tourDetails;

}
