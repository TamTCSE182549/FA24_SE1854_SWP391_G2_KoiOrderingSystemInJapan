package fall24.swp391.KoiOrderingSystem.pojo;

import fall24.swp391.KoiOrderingSystem.enums.Route;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

@Entity
@Table(name = "delivery_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "route")
    private Route route;

    @Column(name = "health_koi_description")
    private String healthKoiDescription;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "booking_id")
    private Bookings booking;

    @Column(name = "created_at")
    private LocalDateTime createdDate;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "delivery_staff_id")
    private Account deliveryStaff;

    @PrePersist
    protected void onCreate(){
        createdDate = LocalDateTime.now();
        validateRouteOrder();
    }
    @PreUpdate
    protected void onUpdate() {
//        validateRouteOrder();
    }

    private void validateRouteOrder() {
        if (booking != null && route != null) {
            // Lấy delivery history cuối cùng của booking này
            DeliveryHistory lastDelivery = booking.getDeliveryHistory()
                    .stream()
                    .filter(d -> !d.equals(this)) // Loại trừ record hiện tại
                    .max(Comparator.comparing(DeliveryHistory::getCreatedDate))
                    .orElse(null);

            // Nếu là record đầu tiên, chỉ cho phép TAKE_KOI_AT_FARM
            if (lastDelivery == null && route != Route.TAKE_KOI_AT_FARM) {
                throw new IllegalStateException("First delivery must be TAKE_KOI_AT_FARM");
            }

            // Nếu có record trước đó, kiểm tra thứ tự
            if (lastDelivery != null && !isValidNextRoute(lastDelivery.getRoute(), this.route)) {
                throw new IllegalStateException(
                        String.format("Invalid route transition from %s to %s",
                                lastDelivery.getRoute(), this.route)
                );
            }
        }
    }

    private boolean isValidNextRoute(Route currentRoute, Route nextRoute) {
        if (currentRoute == null || nextRoute == null) {
            return false;
        }

        return currentRoute.ordinal() + 1 == nextRoute.ordinal();
    }
}

