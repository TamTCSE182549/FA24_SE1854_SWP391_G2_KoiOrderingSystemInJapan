package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class FindTourRequest {
    Long farmId;
    Long koiId;
    Float minPrice;
    Float maxPrice;
    String startDate;
    String endDate;
}
