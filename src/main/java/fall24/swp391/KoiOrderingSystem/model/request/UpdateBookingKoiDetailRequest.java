package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

@Data
public class UpdateBookingKoiDetailRequest {
    Long id;
    Long koiId;
    int quantity;
    float unitPrice;
}
