package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class KoiOfFarmResponse {
    private Long koiId;
    private int quantity;
}