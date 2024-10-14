package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.CheckinRequest;
import fall24.swp391.KoiOrderingSystem.model.response.CheckinResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Checkin;

import java.util.List;

public interface ICheckinService {

    List<Checkin> getChekinByBookingId(Long Id);

    Checkin createCheckin(CheckinRequest checkinUserRequest, Long bookingId);

    Checkin updateCheckin(Long Id, CheckinRequest checkinRequest);

    CheckinResponse deleteCheckin(Long Id);
}
