package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

@Data
public class TourDetailRequest {
    Long tourID;
    Long farmID;
    String description;
}
