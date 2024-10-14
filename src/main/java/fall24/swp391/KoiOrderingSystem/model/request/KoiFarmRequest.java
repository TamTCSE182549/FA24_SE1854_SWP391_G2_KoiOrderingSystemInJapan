package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

@Data
public class KoiFarmRequest {
    private String farmName;
    private String farmPhoneNumber;
    private String farmEmail;
    private String farmAddress;
    private String website;
}
