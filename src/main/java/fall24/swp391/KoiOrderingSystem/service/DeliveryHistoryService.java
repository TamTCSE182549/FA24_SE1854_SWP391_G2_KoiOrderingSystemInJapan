package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.exception.AccountNotFoundException;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.request.DeliveryHistoryRequest;
import fall24.swp391.KoiOrderingSystem.model.response.DeliveryHistoryResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.DeliveryHistory;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IDeliveryHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryHistoryService implements IDeliveryHistoryService {

    @Autowired
    IDeliveryHistoryRepository deliveryHistoryRepository;

    @Autowired
    IBookingRepository bookingRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public DeliveryHistoryResponse addDeliveryHistory(DeliveryHistoryRequest deliveryHistoryRequest, Long bookingId) throws Exception {
        try {
            DeliveryHistory deliveryHistory = modelMapper.map(deliveryHistoryRequest, DeliveryHistory.class);
            Optional<Bookings> bookings = bookingRepository.findById(bookingId);
            if (bookings.isPresent()) {
                //tim duoc booking
                Bookings booking = bookings.get();
                if(booking.getPaymentStatus()== PaymentStatus.shipped)
                {
                    deliveryHistory.setBooking(booking);
                }
                else throw new NotFoundEntity("Booking isn't shipped");
            } else {
                throw new NotFoundEntity("Booking not found");
            }
            //tim account cua staff
            Account staffAccount = authenticationService.getCurrentAccount();
            if (staffAccount == null) {
                throw new AccountNotFoundException("Staff not found");
            } else {
                deliveryHistory.setDeliveryStaff(staffAccount);
            }
             deliveryHistoryRepository.save(deliveryHistory);
            DeliveryHistoryResponse deliveryHistoryResponse = new DeliveryHistoryResponse();
            deliveryHistoryResponse.setDeliveryId(deliveryHistory.getId());
            deliveryHistoryResponse.setBookingId(deliveryHistory.getBooking().getId());
            deliveryHistoryResponse.setTime(deliveryHistory.getCreatedDate());
            deliveryHistoryResponse.setStaffName(staffAccount.getLastName()+" "+staffAccount.getFirstName());
            deliveryHistoryResponse.setHealthKoiDescription(deliveryHistory.getHealthKoiDescription());
            deliveryHistoryResponse.setRoute(deliveryHistory.getRoute());
            return deliveryHistoryResponse;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeliveryHistoryResponse updateDeliveryHistory(Long deliveryHistoryId, DeliveryHistoryRequest deliveryHistoryRequest) throws Exception {
        DeliveryHistory deliveryHistory = deliveryHistoryRepository.findDeliveryHistoryById(deliveryHistoryId);
        if (deliveryHistory == null) {
            throw new NotFoundEntity("Delivery History not found");
        }
        deliveryHistory.setRoute(deliveryHistoryRequest.getRoute());
        deliveryHistory.setHealthKoiDescription(deliveryHistoryRequest.getHealthKoiDescription());
        deliveryHistoryRepository.save(deliveryHistory);
        DeliveryHistoryResponse deliveryHistoryResponse = new DeliveryHistoryResponse();
        deliveryHistoryResponse.setBookingId(deliveryHistory.getBooking().getId());
        deliveryHistoryResponse.setTime(deliveryHistory.getCreatedDate());
        deliveryHistoryResponse.setDeliveryId(deliveryHistory.getId());
        deliveryHistoryResponse.setStaffName(deliveryHistory.getDeliveryStaff().getLastName()+" "+deliveryHistory.getDeliveryStaff().getFirstName());
        deliveryHistoryResponse.setHealthKoiDescription(deliveryHistory.getHealthKoiDescription());
        deliveryHistoryResponse.setRoute(deliveryHistory.getRoute());
        return deliveryHistoryResponse;
    }

    @Override
    public void deleteDeliveryHistory(Long deliveryHistoryId) {
        DeliveryHistory deliveryHistory = deliveryHistoryRepository.findDeliveryHistoryById(deliveryHistoryId);
        if (deliveryHistory == null) {
            throw new NotFoundEntity("Delivery History not found");
        }
        deliveryHistoryRepository.delete(deliveryHistory);
    }

    @Override
    public List<DeliveryHistoryResponse> getDeliveryHistory(Long bookingId) {
        List<DeliveryHistory> deliveryHistorys = new ArrayList<>();
        List<DeliveryHistoryResponse> deliveryHistoryResponses = new ArrayList<>();
        try {
            Optional<Bookings> bookings = bookingRepository.findById(bookingId);
            if (bookings.isPresent()) {
                //tim duoc booking
                Bookings booking = bookings.get();
                deliveryHistorys = deliveryHistoryRepository.findDeliveryHistoryByBooking(booking);

                for (DeliveryHistory deliveryHistory : deliveryHistorys) {
                    DeliveryHistoryResponse deliveryHistoryResponse = new DeliveryHistoryResponse();
                    deliveryHistoryResponse.setBookingId(deliveryHistory.getBooking().getId());
                    deliveryHistoryResponse.setDeliveryId(deliveryHistory.getId());
                    deliveryHistoryResponse.setTime(deliveryHistory.getCreatedDate());
                    deliveryHistoryResponse.setStaffName(deliveryHistory.getDeliveryStaff().getLastName()+" "+deliveryHistory.getDeliveryStaff().getFirstName());
                    deliveryHistoryResponse.setHealthKoiDescription(deliveryHistory.getHealthKoiDescription());
                    deliveryHistoryResponse.setRoute(deliveryHistory.getRoute());
                    deliveryHistoryResponses.add(deliveryHistoryResponse);
                }
            } else throw new NotFoundEntity("Booking not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return deliveryHistoryResponses;
    }
}

