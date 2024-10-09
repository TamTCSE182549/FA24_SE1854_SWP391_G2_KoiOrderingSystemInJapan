package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.exception.AccountNotFoundException;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.request.DeliveryRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Deliveries;
import fall24.swp391.KoiOrderingSystem.pojo.DeliveryHistory;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IDeliveriesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DeliveryService implements IDeliveryService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IDeliveriesRepository deliveriesRepository;

    @Autowired
    IBookingRepository bookingRepository;

    @Override
    public Deliveries addDelivery(DeliveryRequest deliveryRequest, Long bookingId) {
        try {
            Deliveries delivery = modelMapper.map(deliveryRequest, Deliveries.class);
            Optional<Bookings> bookings = bookingRepository.findById(bookingId);
            if (bookings.isPresent()) {
                //tim duoc booking
                Bookings booking = bookings.get();
                if(booking.getPaymentStatus()== PaymentStatus.shipped)
                {
                    booking.setPaymentStatus(PaymentStatus.complete);
                    bookingRepository.save(booking);
                    delivery.setBooking(booking);
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
                delivery.setDeliveryStaff(staffAccount);
            }
            return deliveriesRepository.save(delivery);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Deliveries updateDeliveryHistory(Long deliveryId, DeliveryRequest deliveryRequest) throws Exception {
        Deliveries delivery = deliveriesRepository.findDeliveriesById(deliveryId);
        if (delivery == null) {
            throw new NotFoundEntity("Delivery not found");
        }
        delivery.setCustomerName(deliveryRequest.getCustomerName());
        delivery.setReceiveDate(deliveryRequest.getReceiveDate());
        delivery.setHealthKoiDescription(deliveryRequest.getHealthKoiDescription());
        delivery.setRemainAmount(deliveryRequest.getRemainAmount());
        return deliveriesRepository.save(delivery);

    }

    @Override
    public void deleteDelivery(Long deliveryId) {
        deliveriesRepository.deleteById(deliveryId);
    }

    @Override
    public List<Deliveries> getAllDeliveries() {
        return deliveriesRepository.findAll();
    }
}
