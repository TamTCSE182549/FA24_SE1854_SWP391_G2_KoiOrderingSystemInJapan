package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private String vnp_ResponseCode;
    private String vnp_Amount;
    private String vnp_OrderInfo;
    private String vnp_PayDate;
}
