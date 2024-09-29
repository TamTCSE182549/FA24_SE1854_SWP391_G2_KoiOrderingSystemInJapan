package fall24.swp391.KoiOrderingSystem.pojo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "booking_koi_detail")
public class BookingKoiDetail {

//    @JoinColumn(name = "id")
//    @ManyToOne
//    private Kois kois;
//
//    @JoinColumn(name = "id")
//    @ManyToOne
//    private Bookings bookings;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "koi_id")
    private Long koiId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_amount")
    private float totalAmount;
}
