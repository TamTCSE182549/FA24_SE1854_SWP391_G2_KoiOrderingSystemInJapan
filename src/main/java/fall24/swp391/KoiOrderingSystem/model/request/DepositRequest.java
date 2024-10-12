package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class DepositRequest {
    private float shippingFee;

    private Date deliveryExpectedDate;

    private String shippingAddress;

    private float depositPercentage;

}
