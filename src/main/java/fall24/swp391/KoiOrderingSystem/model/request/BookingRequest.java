package fall24.swp391.KoiOrderingSystem.model.request;

import fall24.swp391.KoiOrderingSystem.enums.PaymentMethod;
import lombok.Data;

@Data
public class BookingRequest {
    Long tourID;
    PaymentMethod paymentMethod;
    int participants;
}