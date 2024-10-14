package fall24.swp391.KoiOrderingSystem.model.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CheckinResponse {

        private String firstName;

        private String lastName;

        private String airline;

        private String airport;

        private String nationality;

        private Date checkinDate;

        private Date dateOfBirth;

        private Long bookingId;

        private String passportNumber;

        private LocalDateTime createdDate;

        private LocalDateTime updateDate;

        private  String createBy;

        private String updateBy;
}
