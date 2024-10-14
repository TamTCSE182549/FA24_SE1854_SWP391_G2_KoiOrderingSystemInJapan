package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmImage;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import lombok.Data;

import java.util.List;

@Data
public class KoiFarmResponse {
    private String koiFarmName;
    private String koiFarmPhone;
    private String koiFarmEmail;
    private String koiFarmAddress;
    private String website;
    private List<KoiFarmImage> koiFarmImages;
    private List<KoiOfFarmResponse> koiOfFarms;
}
