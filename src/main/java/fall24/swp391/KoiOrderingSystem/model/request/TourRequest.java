package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TourRequest {
    String tourName;
    float unitPrice;
    int maxParticipants;
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String tourImg;
}
