package fall24.swp391.KoiOrderingSystem.model.response;

import fall24.swp391.KoiOrderingSystem.enums.CheckinStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CheckinResponse {
        private Long id;

        private String firstName;

        private String lastName;

        private String airline;

        private String airport;

        private LocalDate checkinDate;

        private Long bookingId;

        private LocalDateTime createdDate;

        private LocalDateTime updateDate;

        private Long customerId;

        private  String createBy;

        private String updateBy;

        private CheckinStatus status;
}
