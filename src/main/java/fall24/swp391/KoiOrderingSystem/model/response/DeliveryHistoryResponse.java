package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.enums.Route;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryHistoryResponse {
    private Long bookingId;
    private Long deliveryId;
    private Route route;
    private String healthKoiDescription;
    private String staffName;
    private LocalDateTime time;
}
