package fall24.swp391.KoiOrderingSystem.model.response;

import java.time.LocalDateTime;
import java.util.List;

public class BookingTourCustomResponse {

    long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private List<Long> farmId;

    private int participant;
}
