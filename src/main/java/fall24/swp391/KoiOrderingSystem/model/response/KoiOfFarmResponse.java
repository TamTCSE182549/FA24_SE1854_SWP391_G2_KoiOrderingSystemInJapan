package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class KoiOfFarmResponse {
    private Long Id;
    private Long farmId;
    private Long koiId;
    private int quantity;
    private boolean available;
}
