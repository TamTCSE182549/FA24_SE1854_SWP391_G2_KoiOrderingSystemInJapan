package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.enums.ApproveStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuotationResponse {
    private Long bookingId;
    private float amount;
    private String staffName;
    private String managerName;
    private ApproveStatus isApprove;
    private LocalDateTime approveTime;
}
