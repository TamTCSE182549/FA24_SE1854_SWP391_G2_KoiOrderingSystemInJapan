package fall24.swp391.KoiOrderingSystem.model.request;

import fall24.swp391.KoiOrderingSystem.enums.DeliveryStatus;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class DeliveryHistoryRequest {
    private String route;
    private String healthKoiDescription;

}
