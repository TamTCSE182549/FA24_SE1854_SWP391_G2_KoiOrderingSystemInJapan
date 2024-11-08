package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;

@Data
public class BookingTourDetailResponse {
    Long bookingTourDetailID;
    Long bookingID;
    Long tourID;
    String tourName;
    Integer participant;
    Float totalAmount;
}
