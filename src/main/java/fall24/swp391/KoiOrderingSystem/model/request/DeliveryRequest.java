package fall24.swp391.KoiOrderingSystem.model.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;
@Data
public class DeliveryRequest {
    private String customerName;

    private Date receiveDate;

    private String healthKoiDescription;

    private float remainAmount;
}
