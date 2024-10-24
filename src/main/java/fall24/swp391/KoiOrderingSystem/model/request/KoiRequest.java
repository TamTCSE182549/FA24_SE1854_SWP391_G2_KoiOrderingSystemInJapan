package fall24.swp391.KoiOrderingSystem.model.request;

import fall24.swp391.KoiOrderingSystem.pojo.KoiImage;
import lombok.Data;

import java.util.List;

@Data
public class KoiRequest {
    private String koiName;

    private List<KoiImageRequest> koiImageList;

    private String description;

    private String color;

    private String origin;


//    private Long categoryId;


}
