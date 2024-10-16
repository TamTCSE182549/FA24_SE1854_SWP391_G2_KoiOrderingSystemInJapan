package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

@Data
public class KoiRequest {
    private String koiName;

    private String koiImage;

    private String description;

    private String color;

    private String origin;

    private Long categoryId;

    private boolean isActive;

}
