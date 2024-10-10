package fall24.swp391.KoiOrderingSystem.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "booking_tour_detail")
public class BookingTourDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "booking_id") //name same foreign key mapping
    private Bookings booking;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "tour_id")
    private Tours tourId;

    @Column(name = "participant")
    @Min(value = 1)
    private int participant;

    @Column(name = "total_amount")
    private float totalAmount;

    public BookingTourDetail(Bookings booking, Tours tourId, int participant) {
        this.booking = booking;
        this.tourId = tourId;
        this.participant = participant;
    }
}
