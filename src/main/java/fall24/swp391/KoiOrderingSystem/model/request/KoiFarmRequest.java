package fall24.swp391.KoiOrderingSystem.model.request;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmImage;
import lombok.Data;

import java.util.List;

@Data
public class KoiFarmRequest {
    private String farmName;
    private String farmPhoneNumber;
    private String farmEmail;
    private String farmAddress;
    private String website;
    private String description;
    private boolean active;
    private List<String> images;
}
