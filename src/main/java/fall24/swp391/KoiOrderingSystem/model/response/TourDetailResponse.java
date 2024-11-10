package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmImage;
import lombok.Data;

import java.util.List;

@Data
public class TourDetailResponse {
    Long id;
    String tourName;
    String farmName;
    String address;
    String website;
    String phone;
    String description;
}
