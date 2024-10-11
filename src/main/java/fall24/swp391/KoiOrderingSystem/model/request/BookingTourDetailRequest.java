package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

@Data
public class BookingTourDetailRequest {
    Long bookingID;
    Long tourID;
    int participant;
}
