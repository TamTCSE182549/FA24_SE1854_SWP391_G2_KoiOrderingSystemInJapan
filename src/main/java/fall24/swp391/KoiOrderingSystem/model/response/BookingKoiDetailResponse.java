package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;

@Data
public class BookingKoiDetailResponse {
    Long bookingKoiDetailId;
    Long bookingId;
    Long koiId;
    Long farmId;
    int quantity;
    float totalAmount;
    float unitPrice;
    String farmName;
    String koiName;
    String origin;
    String description;
    String color;
}
