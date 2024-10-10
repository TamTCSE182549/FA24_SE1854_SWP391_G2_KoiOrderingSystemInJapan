package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TourResponse {
    Long id;
    String tourName;
    float unitPrice;
    int maxParticipants;
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String tourImg;
    String createdBy;
    LocalDateTime createdDate;
    String updatedBy;
    LocalDateTime updatedDate;
}
