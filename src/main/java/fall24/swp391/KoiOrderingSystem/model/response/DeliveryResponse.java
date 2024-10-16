package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.enums.DeliveryStatus;
import lombok.Data;

import java.util.Date;
@Data
public class DeliveryResponse {
    private Long bookingId;
    private String customerName;
    private Date receiveDate;
    private String healthKoiDescription;
    private float remainAmount;
    private String staffName;
    private DeliveryStatus status;
}
