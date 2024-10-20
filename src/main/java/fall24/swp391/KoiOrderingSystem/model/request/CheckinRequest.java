package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class CheckinRequest {
    private String firstName;

    private String lastName;

    private LocalDate checkinDate;

    private String airline;

    private String airport;
}
