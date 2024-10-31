package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedbackResponse {
    Long id;
    String content;
    int rating;
    Long bookingId;
    Long customerId;
    String customerName;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
}
