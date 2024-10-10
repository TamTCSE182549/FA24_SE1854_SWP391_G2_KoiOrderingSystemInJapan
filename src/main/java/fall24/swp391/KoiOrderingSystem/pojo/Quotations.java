package fall24.swp391.KoiOrderingSystem.pojo;

import fall24.swp391.KoiOrderingSystem.enums.ApproveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "quotations")
public class Quotations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "booking_id")
    private Bookings booking;

    @Column(name = "amount")
    private float amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_approve")
    private ApproveStatus isApprove;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "approve_by")
    private Account approveBy;

    @Column(name = "approve_time")
    private LocalDateTime approveTime;

}
