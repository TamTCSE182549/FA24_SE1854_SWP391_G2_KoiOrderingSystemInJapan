package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;

@Data
public class TourDetailResponse {
    Long id;
    String tourName;
    String farmName;
    String description;
}
