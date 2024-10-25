package fall24.swp391.KoiOrderingSystem.model.request;

import fall24.swp391.KoiOrderingSystem.enums.TourStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TourRequestAdmin {
    String tourName;
    float unitPrice;
    int maxParticipants;
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String tourImg;
    TourStatus status;
}
