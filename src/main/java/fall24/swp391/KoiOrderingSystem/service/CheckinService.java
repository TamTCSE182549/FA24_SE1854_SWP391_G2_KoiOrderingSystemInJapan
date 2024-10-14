package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.CheckinStatus;
import fall24.swp391.KoiOrderingSystem.exception.NotCreateException;
import fall24.swp391.KoiOrderingSystem.model.request.CheckinRequest;
import fall24.swp391.KoiOrderingSystem.model.response.CheckinResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Checkin;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.ICheckinRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckinService implements ICheckinService{

    @Autowired
    private ICheckinRepository checkinRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Checkin> getChekinByBookingId(Long Id) {
        List<Checkin> checkinList =checkinRepository.findByBookingId(Id);
        return checkinList;
    }

    @Override
    public Checkin createCheckin(CheckinRequest checkinRequest, Long bookingId) {
        try {
            Bookings booking = bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));
            Checkin checkin =modelMapper.map(checkinRequest,Checkin.class);
            checkin.setBooking(booking);
            checkin.setStatus(CheckinStatus.NOTCHECKEDIN);
            return checkinRepository.save(checkin);
        }catch (Exception e){
            throw new NotCreateException(e.getMessage());
        }
    }

    @Override
    public Checkin updateCheckin(Long Id, CheckinRequest checkinRequest) {
        Checkin Checkin = checkinRepository.findById(Id)
                .orElseThrow(() ->new RuntimeException("Checkin Id not found"));

        Checkin.setAirline(checkinRequest.getAirline());

            if (Checkin.getStatus() == CheckinStatus.NOTCHECKEDIN) {
                Checkin.setStatus(CheckinStatus.CHECKED);
            }
        Checkin.setAirport(checkinRequest.getAirport());

        Checkin.setCheckinDate(checkinRequest.getCheckinDate());

        Checkin.setDateOfBirth(checkinRequest.getDateOfBirth());

        Checkin.setFirstName(checkinRequest.getFirstName());

        Checkin.setLastName(checkinRequest.getLastName());

        Checkin.setNationality(checkinRequest.getNationality());

        Checkin.setPassportNumber(checkinRequest.getPassportNumber());


        return checkinRepository.save(Checkin);
    }

    @Override
    public CheckinResponse deleteCheckin(Long Id) {
        Checkin deleteCheckin = checkinRepository.findById(Id)
                .orElseThrow(() ->new RuntimeException("Checkin Id not found"));
            deleteCheckin.setStatus(CheckinStatus.CANCELLED);
            checkinRepository.save(deleteCheckin);
            return modelMapper.map(deleteCheckin,CheckinResponse.class);
    }
}
