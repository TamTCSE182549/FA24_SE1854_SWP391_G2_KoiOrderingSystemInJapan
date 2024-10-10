package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Checkin;

import java.util.List;

public interface ICheckinService {

    List<Checkin> getChekinByBookingId(Long Id);

    Checkin createCheckin(Checkin checkin, Long bookingId);

    Checkin updateCheckin(Long Id,Checkin checkin);

    Boolean deleteCheckin(Long Id);
}
