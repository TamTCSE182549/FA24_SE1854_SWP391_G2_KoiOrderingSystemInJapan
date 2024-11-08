package fall24.swp391.KoiOrderingSystem.model.request;

import fall24.swp391.KoiOrderingSystem.enums.PaymentMethod;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import lombok.Data;

@Data
public class BookingUpdateRequestStaff {
    Long bookingID;
    float amount;
    float vat;
    PaymentMethod paymentMethod;
    PaymentStatus paymentStatus;
    float discountAmount;
}
