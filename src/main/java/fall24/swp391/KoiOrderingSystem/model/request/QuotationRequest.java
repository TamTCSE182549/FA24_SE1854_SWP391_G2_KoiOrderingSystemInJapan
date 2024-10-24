package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

@Data
public class QuotationRequest {
    Long bookingId;
    float amount;
    String description;

}
