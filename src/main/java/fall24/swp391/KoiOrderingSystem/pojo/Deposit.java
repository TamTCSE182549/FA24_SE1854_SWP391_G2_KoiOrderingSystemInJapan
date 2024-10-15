package fall24.swp391.KoiOrderingSystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.api.client.util.DateTime;
import fall24.swp391.KoiOrderingSystem.enums.DepositStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "deposit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "deposit_amount")
    private float depositAmount;

    @Column(name = "remain_amount")
    private float remainAmount;

    @Column(name = "deposit_date")
    private LocalDateTime depositDate;

    @Column(name ="deposit_percentage")
    private float depositPercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "deposit_status")
    private DepositStatus depositStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "delivery_expected_date")
    private LocalDate deliveryExpectedDate;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "shipping_fee")
    private float shippingFee;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "booking_id")
    private Bookings booking;

    @PrePersist
    protected void onCreate(){
        depositDate = LocalDateTime.now();
    }
}
