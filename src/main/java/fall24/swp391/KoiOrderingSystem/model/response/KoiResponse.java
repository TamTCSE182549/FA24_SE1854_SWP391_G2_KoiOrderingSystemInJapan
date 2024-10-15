package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;

@Data
public class KoiResponse {

    private String koiName;

    private String koiImage;

    private String description;

    private String color;

    private String origin;

    private Long categoryId;

    private boolean isActive;

}
