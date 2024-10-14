package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

@Data
public class KoiOfFarmRequest {

    private Long koiId;

    private Long farmId;

    private int quantity;

    private boolean available;
}

