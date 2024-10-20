package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.model.request.KoiImageRequest;
import fall24.swp391.KoiOrderingSystem.pojo.KoiImage;
import lombok.Data;

import java.util.List;

@Data
public class KoiResponse {
    private Long id;

    private String koiName;

    private List<KoiImageRequest> koiImageList;

    private String description;

    private String color;

    private String origin;

//    private Long categoryId;

    private boolean isActive;

}
