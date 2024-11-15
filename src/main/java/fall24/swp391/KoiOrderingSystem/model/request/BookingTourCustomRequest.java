package fall24.swp391.KoiOrderingSystem.model.request;

import fall24.swp391.KoiOrderingSystem.enums.PaymentMethod;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingTourCustomRequest {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<Long> farmId;

    private int participant;

    private PaymentMethod paymentMethod;

    private String description;
}
