package fall24.swp391.KoiOrderingSystem.pojo;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonBackReference
    @JoinColumn(name = "koi_id")
    private Kois koi;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonBackReference
    @JoinColumn(name = "booking_id")
    private Bookings booking;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_amount")
    private float totalAmount;

    @Column(name = "unit_Price")
    private float unitPrice;

    public BookingKoiDetail(Bookings booking, Kois koi, int quantity,float unitPrice) {
        this.booking =booking;
        this.koi=koi;
        this.quantity=quantity;
        this.unitPrice=unitPrice;
    }
}
