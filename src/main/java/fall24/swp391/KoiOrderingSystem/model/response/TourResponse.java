package fall24.swp391.KoiOrderingSystem.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    String description;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String tourImg;
    TourStatus tourStatus;
    String createdBy;
    LocalDateTime createdDate;
    String updatedBy;
    LocalDateTime updatedDate;
}
