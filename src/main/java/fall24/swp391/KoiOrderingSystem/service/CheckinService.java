package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.CheckinStatus;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Checkin;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.ICheckinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CheckinService implements ICheckinService{

    @Autowired
    private ICheckinRepository checkinRepository;

    @Autowired
    private IBookingRepository bookingRepository;


    @Override
    public List<Checkin> getChekinByBookingId(Long Id) {
        List<Checkin> checkinList =checkinRepository.findByBookingId(Id);
        return checkinList;
    }

    @Override
    public Checkin createCheckin(Checkin checkin,Long bookingId) {
        Bookings booking = bookingRepository.findBookingsById(bookingId);
        if(booking == null){
            throw new NotFoundEntity("Booking not found");
        }
        checkin.setBooking(booking);
        checkin.setStatus(CheckinStatus.NOTCHECKEDIN);
        return checkinRepository.save(checkin);
    }

    @Override
    public Checkin updateCheckin(Long Id, Checkin checkinDetail) {
        Optional<Checkin> existingCheckin = checkinRepository.findById(Id);
        Checkin checkinUpdate = null;
        if (existingCheckin.isPresent()) {
            checkinUpdate = existingCheckin.get();
            checkinUpdate.setAirline(checkinDetail.getAirline());

            checkinUpdate.setStatus(CheckinStatus.CHECKED);

            checkinUpdate.setAirport(checkinDetail.getAirport());

            checkinUpdate.setCheckinDate(checkinDetail.getCheckinDate());

            checkinUpdate.setDateOfBirth(checkinDetail.getDateOfBirth());

            checkinUpdate.setFirstName(checkinDetail.getFirstName());

            checkinUpdate.setLastName(checkinDetail.getLastName());

            checkinUpdate.setNationality(checkinDetail.getNationality());

            checkinUpdate.setPassportNumber(checkinDetail.getPassportNumber());

            checkinUpdate.setStatus(CheckinStatus.CHECKED);
        }
        return checkinRepository.save(checkinUpdate);
    }

    @Override
    public Boolean deleteCheckin(Long Id) {
        Optional<Checkin> deleteCheckin = checkinRepository.findById(Id);
        if(deleteCheckin.isPresent()){
            Checkin checkin = deleteCheckin.get();
            checkin.setStatus(CheckinStatus.CANCELLED);
            checkinRepository.save(checkin);
            return true;
        }
        return false;
    }
}
