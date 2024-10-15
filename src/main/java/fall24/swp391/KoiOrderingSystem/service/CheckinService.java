package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.CheckinStatus;
import fall24.swp391.KoiOrderingSystem.enums.Role;
import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotCreateException;
import fall24.swp391.KoiOrderingSystem.model.request.CheckinRequest;
import fall24.swp391.KoiOrderingSystem.model.response.CheckinResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Checkin;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.ICheckinRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckinService implements ICheckinService {

    @Autowired
    private ICheckinRepository checkinRepository;

    @Autowired
    private IBookingRepository bookingRepository;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CheckinResponse> getChekinByBookingId(Long Id) {
        List<Checkin> checkinList = checkinRepository.findByBookingId(Id);
        if (checkinList.isEmpty()) {
            throw new GenericException("Not Found Checkin");
        }
        return checkinList.stream().map(checkin -> modelMapper.map(checkin, CheckinResponse.class)).toList();
    }

    @Override
    public CheckinResponse createCheckin(CheckinRequest checkinRequest, Long bookingId) {
        try {

            Account account = authenticationService.getCurrentAccount();
            if (account.getRole() == Role.SALES_STAFF) {
                Bookings booking = bookingRepository.findById(bookingId)
                        .orElseThrow(() -> new RuntimeException("Booking not found"));
                Checkin checkin = modelMapper.map(checkinRequest, Checkin.class);
                checkin.setBooking(booking);
                checkin.setStatus(CheckinStatus.NOTCHECKEDIN);
                checkinRepository.save(checkin);
                CheckinResponse checkinResponse = modelMapper.map(checkin, CheckinResponse.class);
                checkinResponse.setCreateBy(account.getFirstName() + " " + account.getLastName());
                return checkinResponse;
            } else {
                throw new NotCreateException("Only staff can create");
            }
        } catch (Exception e) {
            throw new NotCreateException(e.getMessage());
        }
    }

    @Override
    public CheckinResponse updateCheckin(Long Id, CheckinRequest checkinRequest) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole() == Role.SALES_STAFF) {
                Checkin checkin = checkinRepository.findById(Id)
                        .orElseThrow(() -> new RuntimeException("Checkin Id not found"));

                checkin.setAirline(checkinRequest.getAirline());

                if (checkin.getStatus() == CheckinStatus.NOTCHECKEDIN) {
                    checkin.setStatus(CheckinStatus.CHECKED);
                }
                checkin.setAirport(checkinRequest.getAirport());
                checkin.setCheckinDate(checkinRequest.getCheckinDate());
                checkin.setFirstName(checkinRequest.getFirstName());
                checkin.setLastName(checkinRequest.getLastName());
                checkinRepository.save(checkin);
                CheckinResponse checkinResponse = modelMapper.map(checkin, CheckinResponse.class);
                checkinResponse.setCreateBy(checkin.getCreatedBy().getFirstName() + " " + checkin.getCreatedBy().getLastName());
                checkinResponse.setUpdateBy(account.getFirstName() + " " + account.getLastName());
                return checkinResponse;
            } else {
                throw new NotCreateException("Only staff can create");
            }
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public CheckinResponse deleteCheckin(Long Id) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole() == Role.SALES_STAFF) {
                Checkin deleteCheckin = checkinRepository.findById(Id)
                        .orElseThrow(() -> new RuntimeException("Checkin Id not found"));
                deleteCheckin.setStatus(CheckinStatus.CANCELLED);
                checkinRepository.save(deleteCheckin);
                CheckinResponse checkinResponse = modelMapper.map(deleteCheckin, CheckinResponse.class);
                checkinResponse.setCreateBy(deleteCheckin.getCreatedBy().getFirstName() + " " + deleteCheckin.getCreatedBy().getLastName());
                checkinResponse.setUpdateBy(account.getFirstName() + " " + account.getLastName());
                return checkinResponse;
            } else {
                throw new NotCreateException("Only staff can create");
            }
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}