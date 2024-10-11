package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;

@Data
public class BookingTourDetailResponse {
    Long bookingTourDetailID;
    String tourName;
    int participant;
    float totalAmount;
}
