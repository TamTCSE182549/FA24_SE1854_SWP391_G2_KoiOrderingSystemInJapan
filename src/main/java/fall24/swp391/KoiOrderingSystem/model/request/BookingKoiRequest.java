package fall24.swp391.KoiOrderingSystem.model.request;


import fall24.swp391.KoiOrderingSystem.enums.PaymentMethod;
import fall24.swp391.KoiOrderingSystem.pojo.BookingKoiDetail;
import lombok.Data;

import java.util.List;

@Data
public class BookingKoiRequest {
    Long koiId;
    PaymentMethod paymentMethod;
    List<BookingKoiDetail> koiDetails;

}
