package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.enums.ApproveStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuotationResponse {
    Long id;
    Long bookingId;
    float amount;
    String description;
    String staffName;
    String managerName;
    ApproveStatus isApprove;
    LocalDateTime approveTime;
}
