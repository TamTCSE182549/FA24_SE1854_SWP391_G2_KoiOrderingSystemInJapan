package fall24.swp391.KoiOrderingSystem.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import fall24.swp391.KoiOrderingSystem.enums.DepositStatus;
import fall24.swp391.KoiOrderingSystem.enums.PaymentMethod;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class DepositRequest {
    private float shippingFee;

    private LocalDate deliveryExpectedDate;

    private String shippingAddress;

    private float depositPercentage;

    private LocalDate depositDate;

    private PaymentMethod paymentMethod;

    private DepositStatus depositStatus;

}
