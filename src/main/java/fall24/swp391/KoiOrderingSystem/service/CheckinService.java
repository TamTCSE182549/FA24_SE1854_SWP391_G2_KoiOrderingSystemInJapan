package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.CheckinStatus;
import fall24.swp391.KoiOrderingSystem.enums.Role;
import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotCreateException;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
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

import java.util.ArrayList;
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
        Bookings booking = bookingRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        List<Checkin> checkinList = checkinRepository.findByBookingTour(booking);
        if (checkinList.isEmpty()) {
            throw new GenericException("Not Found Checkin");
        }
        List<CheckinResponse> checkinResponseList = new ArrayList<>();
        for (Checkin checkin : checkinList) {
            CheckinResponse checkinResponse = new CheckinResponse();

            // Set giá trị cho checkinResponse từ checkin
            checkinResponse.setId(checkin.getId());
            checkinResponse.setFirstName(checkin.getFirstName());
            checkinResponse.setLastName(checkin.getLastName());
            checkinResponse.setCheckinDate(checkin.getCheckinDate());
            checkinResponse.setAirline(checkin.getAirline());
            checkinResponse.setAirport(checkin.getAirport());
            checkinResponse.setPassport(checkin.getPassport());
            checkinResponse.setStatus(checkin.getStatus());
            checkinResponse.setPhoneNumber(checkin.getPhoneNumber());
            checkinResponse.setEmail(checkin.getEmail());
            checkinResponse.setCreateBy(checkin.getCreatedBy().getFirstName() + " " + checkin.getCreatedBy().getLastName());
            checkinResponse.setUpdateBy(checkin.getUpdatedBy() != null ? checkin.getUpdatedBy().getFirstName() + " " + checkin.getUpdatedBy().getLastName() : null);
            checkinResponse.setBookingId(checkin.getBookingTour() != null ? checkin.getBookingTour().getId() : null);
            checkinResponseList.add(checkinResponse);
        }
            return checkinResponseList;
    }


    @Override
    public List<CheckinResponse> getChekinstatusByBookingId(Long Id) {
        Bookings booking = bookingRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        List<Checkin> checkinList = checkinRepository.findByBookingTour(booking);
        if (checkinList.isEmpty()) {
            throw new GenericException("Not Found Checkin");
        }
        List<CheckinResponse> checkinResponseList = new ArrayList<>();
        for (Checkin checkin : checkinList) {
            if (checkin.getStatus() == CheckinStatus.CHECKED) {
                CheckinResponse checkinResponse = new CheckinResponse();

                // Set giá trị cho checkinResponse từ checkin
                checkinResponse.setId(checkin.getId());
                checkinResponse.setFirstName(checkin.getFirstName());
                checkinResponse.setLastName(checkin.getLastName());
                checkinResponse.setCheckinDate(checkin.getCheckinDate());
                checkinResponse.setAirline(checkin.getAirline());
                checkinResponse.setAirport(checkin.getAirport());
                checkinResponse.setPassport(checkin.getPassport());
                checkinResponse.setStatus(checkin.getStatus());
                checkinResponse.setPhoneNumber(checkin.getPhoneNumber());
                checkinResponse.setEmail(checkin.getEmail());
                checkinResponse.setCreateBy(checkin.getCreatedBy().getFirstName() + " " + checkin.getCreatedBy().getLastName());
                checkinResponse.setUpdateBy(checkin.getUpdatedBy() != null ? checkin.getUpdatedBy().getFirstName() + " " + checkin.getUpdatedBy().getLastName() : null);
                checkinResponse.setBookingId(checkin.getBookingTour() != null ? checkin.getBookingTour().getId() : null);
                checkinResponseList.add(checkinResponse);
            }
        }
        return checkinResponseList;
    }

    @Override
    public List<Checkin> getCheckinByAccount() {
        Account account = authenticationService.getCurrentAccount();
        List<Checkin> checkinList = checkinRepository.findByCustomerId(account.getId());
        if (checkinList.isEmpty()) {
            throw new GenericException("No check-ins found for this account");
        }
        return checkinList.stream()
                .map(checkin -> modelMapper.map(checkin, Checkin.class))
                .toList();
    }

    @Override
    public List<Checkin> getAllCheckin() {
        List<Checkin> checkinList = checkinRepository.findAll();
        if (checkinList.isEmpty()) {
            throw new GenericException("Not Found Checkin");
        }
        return checkinList.stream().map(checkin -> modelMapper.map(checkin, Checkin.class)).toList();
    }

    @Override
    public CheckinResponse createCheckin(CheckinRequest checkinRequest, Long bookingId) {
        try {

            Account account = authenticationService.getCurrentAccount();
            if (account.getRole() == Role.SALES_STAFF || account.getRole() == Role.CUSTOMER) {
                Bookings booking = bookingRepository.findById(bookingId)
                        .orElseThrow(() -> new RuntimeException("Booking not found"));

//                Checkin checkin = modelMapper.map(checkinRequest, Checkin.class);
//                checkin.setBooking(booking);
//                checkin.setStatus(CheckinStatus.NOTCHECKEDIN);
//                checkin.setCreatedBy(account);
//                checkin.setCustomerId(booking.getAccount());
//                checkinRepository.save(checkin);
//                CheckinResponse checkinResponse = modelMapper.map(checkin, CheckinResponse.class);
//                checkinResponse.setCreateBy(account.getFirstName() + " " + account.getLastName());
                Checkin checkin = new Checkin();
                checkin.setFirstName(checkinRequest.getFirstName());
                checkin.setLastName(checkinRequest.getLastName());
                checkin.setCheckinDate(checkinRequest.getCheckinDate());
                checkin.setAirline(checkinRequest.getAirline());
                checkin.setAirport(checkinRequest.getAirport());
                checkin.setPassport(checkinRequest.getPassport());
                checkin.setEmail(checkinRequest.getEmail());
                checkin.setPhoneNumber(checkinRequest.getPhoneNumber());

                checkin.setStatus(CheckinStatus.NOTCHECKEDIN);
                checkin.setCreatedBy(account);
                checkin.setPassport(checkin.getPassport());
                checkin.setCustomerId(booking.getAccount());

                // Thiết lập booking vào checkin
                checkin.setBookingTour(booking);  // Hoặc setBookingKoi(booking) nếu sử dụng quan hệ bookingKoi

                checkinRepository.save(checkin);


                // Tạo CheckinResponse và thiết lập thủ công các giá trị
                CheckinResponse checkinResponse = new CheckinResponse();
                checkinResponse.setId(checkin.getId());
                checkinResponse.setFirstName(checkin.getFirstName());
                checkinResponse.setLastName(checkin.getLastName());
                checkinResponse.setCheckinDate(checkin.getCheckinDate());
                checkinResponse.setAirline(checkin.getAirline());
                checkinResponse.setAirport(checkin.getAirport());
                checkinResponse.setPassport(checkin.getPassport());
                checkinResponse.setStatus(checkin.getStatus());
                checkinResponse.setEmail(checkin.getEmail());
                checkinResponse.setPhoneNumber(checkinRequest.getPhoneNumber());
                checkinResponse.setCreateBy(account.getFirstName() + " " + account.getLastName());
                checkinResponse.setCustomerId(booking.getAccount().getId());

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
            if (account.getRole() == Role.SALES_STAFF  || account.getRole() == Role.CUSTOMER) {
                Checkin checkin = checkinRepository.findById(Id)
                        .orElseThrow(() -> new RuntimeException("Checkin Id not found"));

                checkin.setAirline(checkinRequest.getAirline());
                checkin.setAirport(checkinRequest.getAirport());
                checkin.setCheckinDate(checkinRequest.getCheckinDate());
                checkin.setFirstName(checkinRequest.getFirstName());
                checkin.setLastName(checkinRequest.getLastName());
                checkin.setUpdatedBy(account);
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
    public Checkin updateCheckinStatus(Long Id) {
        try{
            Account account = authenticationService.getCurrentAccount();
            if(account.getRole() == Role.SALES_STAFF){
                Checkin checkin = checkinRepository.findById(Id)
                        .orElseThrow(() -> new RuntimeException("Checkin Not Found"));
                if(checkin.getStatus() == CheckinStatus.NOTCHECKEDIN){
                    checkin.setStatus(CheckinStatus.CHECKED);
                }
                checkin.setUpdatedBy(account);
                checkinRepository.save(checkin);

//                CheckinResponse checkinResponse =modelMapper.map(checkin,CheckinResponse.class);
//                checkinResponse.setUpdateBy(account.getFirstName()+" "+account.getLastName());
//                return checkinResponse;
                return checkin;
            }else{
                throw new NotUpdateException("Only staff can update status");
            }
        }catch (Exception e){
            throw new GenericException(e.getMessage());
        }

    }

    @Override
    public CheckinResponse deleteCheckin(Long Id) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole() == Role.SALES_STAFF  || account.getRole() == Role.CUSTOMER) {
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