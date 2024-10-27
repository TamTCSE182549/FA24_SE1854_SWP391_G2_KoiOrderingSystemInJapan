package fall24.swp391.KoiOrderingSystem.model.request;

import fall24.swp391.KoiOrderingSystem.enums.PaymentMethod;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import lombok.Data;

@Data
public class BookingUpdate {
    float vat;
    PaymentMethod paymentMethod;
    float discountAmount;
}
