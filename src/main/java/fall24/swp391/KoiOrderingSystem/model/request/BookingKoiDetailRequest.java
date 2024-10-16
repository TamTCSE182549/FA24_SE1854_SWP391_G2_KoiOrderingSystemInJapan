package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

@Data
public class BookingKoiDetailRequest {
    Long koiId;
    int quantity;
    float unitPrice;
}
