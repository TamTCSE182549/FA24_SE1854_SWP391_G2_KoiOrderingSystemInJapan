package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;

@Data
public class BookingKoiDetailResponse {
    Long bookingKoiDetailId;
    Long bookingId;
    Long koiId;
    int quantity;
    float totalAmount;
    float unitPrice;
}
