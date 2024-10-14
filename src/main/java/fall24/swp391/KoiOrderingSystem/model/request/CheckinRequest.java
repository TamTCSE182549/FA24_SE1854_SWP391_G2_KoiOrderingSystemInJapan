package fall24.swp391.KoiOrderingSystem.model.request;

import lombok.Data;

import java.util.Date;

@Data
public class CheckinRequest {
    private String firstName;

    private String lastName;

    private String nationality;

    private Date dateOfBirth;

    private String passportNumber;

    private Date checkinDate;

    private String airline;

    private String airport;
}
