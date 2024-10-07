package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.exception.AccountNotFoundException;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.request.DeliveryHistoryRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.DeliveryHistory;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IDeliveryHistoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryHistoryService implements IDeliveryHistoryService {

    @Autowired
    IDeliveryHistoryRepository deliveryHistoryRepository;

    @Autowired
    IBookingRepository bookingRepository;


    @Autowired
    ModelMapper modelMapper;

    @Override
    public DeliveryHistory addDeliveryHistory(DeliveryHistoryRequest deliveryHistoryRequest, Long bookingId) throws Exception {
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
            Account staffAccount = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (staffAccount == null) {
                throw new AccountNotFoundException("Staff not found");
            } else {
                deliveryHistory.setDeliveryStaff(staffAccount);
            }
            return deliveryHistoryRepository.save(deliveryHistory);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeliveryHistory updateDeliveryHistory(Long deliveryHistoryId, DeliveryHistoryRequest deliveryHistoryRequest) throws Exception {
        DeliveryHistory deliveryHistory = deliveryHistoryRepository.findDeliveryHistoryById(deliveryHistoryId);
        if (deliveryHistory == null) {
            throw new NotFoundEntity("Delivery History not found");
        }
        deliveryHistory.setRoute(deliveryHistoryRequest.getRoute());
        deliveryHistory.setHealthKoiDescription(deliveryHistoryRequest.getHealthKoiDescription());
        return deliveryHistoryRepository.save(deliveryHistory);
    }

    @Override
    public void deleteDeliveryHistory(Long deliveryHistoryId) {
        deliveryHistoryRepository.deleteById(deliveryHistoryId);
    }

    @Override
    public List<DeliveryHistory> getDeliveryHistory(Long bookingId) {
        List<DeliveryHistory> deliveryHistory = null;
        try {
            Optional<Bookings> bookings = bookingRepository.findById(bookingId);
            if (bookings.isPresent()) {
                //tim duoc booking
                Bookings booking = bookings.get();
                deliveryHistory = deliveryHistoryRepository.findDeliveryHistoryByBooking(booking);
            } else throw new NotFoundEntity("Booking not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return deliveryHistory;
    }
}

