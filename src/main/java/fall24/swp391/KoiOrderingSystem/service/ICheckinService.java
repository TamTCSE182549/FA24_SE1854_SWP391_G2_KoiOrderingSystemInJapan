package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.CheckinRequest;
import fall24.swp391.KoiOrderingSystem.model.response.CheckinResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Checkin;

import java.util.List;

public interface ICheckinService {

    List<CheckinResponse> getChekinByBookingId(Long Id);

    CheckinResponse createCheckin(CheckinRequest checkinUserRequest, Long bookingId);

    CheckinResponse updateCheckin(Long Id, CheckinRequest checkinRequest);

    Checkin updateCheckinStatus(Long Id);

    CheckinResponse deleteCheckin(Long Id);

    List<Checkin> getAllCheckin();



    List<CheckinResponse> getChekinstatusByBookingId(Long Id);

    List<Checkin> getCheckinByAccount();

}