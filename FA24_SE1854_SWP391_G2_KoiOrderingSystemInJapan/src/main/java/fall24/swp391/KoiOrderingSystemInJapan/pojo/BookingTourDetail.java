package fall24.swp391.KoiOrderingSystemInJapan.pojo;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "booking_tour_detail")
public class BookingTourDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "booking_id")
    private Bookings booking;

    @Column(name = "tour_id")
    private int tourId;

    @Column(name = "participant")
    private int participant;

    @Column(name = "total_amount")
    private float totalAmount;

}
