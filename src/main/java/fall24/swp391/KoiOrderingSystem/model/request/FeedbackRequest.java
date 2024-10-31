package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

@Data
public class FeedbackRequest {
    private String content;
    private int rating;
    private Long bookingId;
}
