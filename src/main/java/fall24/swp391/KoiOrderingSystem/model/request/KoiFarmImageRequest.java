package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

@Data
public class KoiFarmImageRequest {
    private Long farmId;
    private String imageUrl;
}
