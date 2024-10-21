package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmImage;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import lombok.Data;

import java.util.List;

@Data
public class KoiFarmResponse {
    private  Long id;
    private String farmName;
    private String farmPhoneNumber;
    private String farmEmail;
    private String farmAddress;
    private String website;
    private String description;
    private List<KoiFarmImageResponse> koiFarmImages;
    private List<KoiOfFarmResponse> koiOfFarms;
    private List<KoiResponse> koiResponses;
}
