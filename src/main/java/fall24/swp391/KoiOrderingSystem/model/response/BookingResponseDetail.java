package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.enums.BookingType;
import fall24.swp391.KoiOrderingSystem.enums.PaymentMethod;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.pojo.BookingKoiDetail;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class BookingResponseDetail {
    Long id;
    Long customerID;
    String nameCus;
    float totalAmount;
    float vat;
    float vatAmount;
    float discountAmount;
    float totalAmountWithVAT;
    BookingType bookingType;
    PaymentMethod paymentMethod;
    PaymentStatus paymentStatus;
    String createdBy;
    LocalDateTime createdDate;
    String updatedBy;
    LocalDateTime updatedDate;
    List<BookingKoiDetail> koiDetails;
}
