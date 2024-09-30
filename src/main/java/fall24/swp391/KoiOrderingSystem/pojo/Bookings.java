package fall24.swp391.KoiOrderingSystem.pojo;

import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "bookings")
public class Bookings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "booking_type_id")
    private BookingType bookingType;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    @Column(name = "total_amount")
    private float totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "payment_method")
    private String paymentMethod;

    //here mapping quotation table
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quotation_id")
    private Quotations quotations;

    //mapped By same name with ManyToOne annotation
    @OneToMany(mappedBy = "booking", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<BookingTourDetail> bookingTourDetails;

    public void addBookingDetail(BookingTourDetail bookingTourDetail) {
        if (bookingTourDetails == null) {
            bookingTourDetails = new ArrayList<>();
        }
        bookingTourDetails.add(bookingTourDetail);
        bookingTourDetail.setBooking(this);
    }
}
