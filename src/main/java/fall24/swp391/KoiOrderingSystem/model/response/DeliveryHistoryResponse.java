package fall24.swp391.KoiOrderingSystem.model.response;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryHistoryResponse {
    private Long bookingId;
    private Long deliveryId;
    private String route;
    private String healthKoiDescription;
    private String staffName;
    private LocalDateTime time;
}
