package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class CategoryResponse {
    private Long categoryId;
    private String cateName;
    private String description;
    private List<KoiResponse> kois;
}
