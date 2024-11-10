package fall24.swp391.KoiOrderingSystem.model.request;

import fall24.swp391.KoiOrderingSystem.enums.DeliveryStatus;
import lombok.Data;

import java.util.Date;
@Data
public class DeliveryRequest {
    private String customerName;

    private String reason;

    private Date receiveDate;

    private String healthKoiDescription;

    private DeliveryStatus status;

    private String address;

//    private float remainAmount;
}
