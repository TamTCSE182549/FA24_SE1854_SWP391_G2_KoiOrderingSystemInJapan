package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.enums.DepositStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Data
public class DepositRespone {

    private  float depositAmount;

    private float  remainAmount;

    private float shippingFee;

    private LocalDate depositDate;

    private LocalDate deliveryExpectedDate;

    private String shippingAddress;

    private float depositPercentage;

    private DepositStatus depositStatus;

    private Long bookingId;

}
