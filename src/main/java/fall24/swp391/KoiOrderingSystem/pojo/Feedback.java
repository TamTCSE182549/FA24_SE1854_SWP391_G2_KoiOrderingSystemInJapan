package fall24.swp391.KoiOrderingSystem.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Account customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "booking_id")
    private Bookings booking;

    @Column(name = "created_at")
    private LocalDateTime createdDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedDate;

    @Column(name = "tour_name_snapshot")
    private String tourNameSnapshot;

    @Column(name = "tour_start_time_snapshot")
    private LocalDateTime tourStartTimeSnapshot;

    @Column(name = "tour_end_time_snapshot")
    private LocalDateTime tourEndTimeSnapshot;

    @PrePersist
    protected void onCreate(){
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
        if (booking != null && booking.getBookingTourDetails().getFirst() != null
                && booking.getBookingTourDetails().getFirst().getTourId() != null) {
            Tours tour = booking.getBookingTourDetails().getFirst().getTourId();
            this.tourNameSnapshot = tour.getTourName();
            this.tourStartTimeSnapshot = tour.getStartTime();
            this.tourEndTimeSnapshot = tour.getEndTime();
        }
    }

    @PreUpdate
    protected void onUpdate(){
        updatedDate = LocalDateTime.now();
    }
}
