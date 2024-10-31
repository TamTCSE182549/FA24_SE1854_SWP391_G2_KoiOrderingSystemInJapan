package fall24.swp391.KoiOrderingSystem.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import fall24.swp391.KoiOrderingSystem.enums.DepositStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class DepositRequest {
    private float shippingFee;

    private LocalDate deliveryExpectedDate;

    private String shippingAddress;

    private float depositPercentage;

    private DepositStatus depositStatus;

}
