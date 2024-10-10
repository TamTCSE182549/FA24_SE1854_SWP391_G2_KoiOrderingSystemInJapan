package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.enums.BookingType;
import fall24.swp391.KoiOrderingSystem.enums.PaymentMethod;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponse {
    Long id;
    float totalAmount;
    float vat;
    float vatAmount;
    float totalAmountWithVAT;
    BookingType bookingType;
    PaymentMethod paymentMethod;
    PaymentStatus paymentStatus;
    String createdBy;
    LocalDateTime createdDate;
    String updatedBy;
    LocalDateTime updatedDate;
}
