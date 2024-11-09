package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.enums.TourStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TourResponse {
    String message;
    Long id;
    String tourName;
    float unitPrice;
    int maxParticipants;
    Integer remaining;
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String tourImg;
    TourStatus status;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
    String createdBy;
    String updatedBy;
}
