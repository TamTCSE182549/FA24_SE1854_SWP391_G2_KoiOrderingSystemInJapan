package fall24.swp391.KoiOrderingSystem.service;


import fall24.swp391.KoiOrderingSystem.component.Email;
import fall24.swp391.KoiOrderingSystem.enums.DeliveryStatus;
import fall24.swp391.KoiOrderingSystem.enums.DepositStatus;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.exception.AccountNotFoundException;
import fall24.swp391.KoiOrderingSystem.exception.ExistingEntity;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.EmailDetail;
import fall24.swp391.KoiOrderingSystem.model.request.DeliveryRequest;
import fall24.swp391.KoiOrderingSystem.model.response.DeliveryResponse;
import fall24.swp391.KoiOrderingSystem.pojo.*;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IDeliveryRepository;
import fall24.swp391.KoiOrderingSystem.repo.IDepositRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService implements IDeliveryService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IDeliveryRepository deliveriesRepository;

    @Autowired
    IBookingRepository bookingRepository;

    @Autowired
    IDepositRepository depositRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    Email email;

    @Override
    public DeliveryResponse addDelivery(DeliveryRequest deliveryRequest, Long bookingId) {
        try {
            Delivery delivery = modelMapper.map(deliveryRequest, Delivery.class);
            EmailDetail emailDetail = new EmailDetail();
            Bookings bookings = bookingRepository.findBookingsById(bookingId);
            Delivery oldDelivery = deliveriesRepository.findDeliveryByBooking(bookings);
            if(oldDelivery != null) {
                throw new ExistingEntity("Delivery this booking already exists!");
            }
            List<Deposit> deposits = depositRepository.findByBookingId(bookingId);
            if(deposits==null) {
                throw new NotFoundEntity("Deposit not found!");
            }
            float remainAmount = 0;
            for (Deposit deposit : deposits) {

                if(deposit.getDepositStatus()== DepositStatus.complete){
                    remainAmount = deposit.getRemainAmount();
                }
            }

            //tim account cua staff
            Account staffAccount = authenticationService.getCurrentAccount();
            if (staffAccount == null) {
                throw new AccountNotFoundException("Staff not found");
            } else {
                delivery.setDeliveryStaff(staffAccount);
                delivery.setRemainAmount(remainAmount);
                if(deliveryRequest.getStatus()== DeliveryStatus.CANCELLED && deliveryRequest.getReason().isEmpty()){
                        throw new NotFoundEntity("CANCELLED must have a reason!");
                }

                //gui mail den khach hang
                if(deliveryRequest.getStatus()== DeliveryStatus.COMPLETED){
                 //   emailDetail.setReceiver(newAccount);
//            emailDetail.setSubject("Thank you for using our system!");
//            emailDetail.setLink("http:/localhost:3000/home");
                } else {
                    //   emailDetail.setReceiver(newAccount);
//            emailDetail.setSubject("Thank you for using our system!");
//            emailDetail.setLink("http:/localhost:3000/home");
                }
                if (bookings!= null) {
                    //tim duoc booking
                    if(bookings.getPaymentStatus()== PaymentStatus.shipped)
                    {
                        bookings.setPaymentStatus(PaymentStatus.complete);
                        bookings.setPaymentDate(LocalDateTime.now());
                        bookingRepository.save(bookings);
                        delivery.setBooking(bookings);
                    }
                    else throw new NotFoundEntity("Booking isn't shipped");
                } else {
                    throw new NotFoundEntity("Booking not found");
                }
                deliveriesRepository.save(delivery);
            }


//            emailDetail.setReceiver(newAccount);
//            emailDetail.setSubject("Hello");
//            emailDetail.setLink("fb.com");
//            email.sendEmail(emailDetail);

            DeliveryResponse deliveryResponse = new DeliveryResponse();
            deliveryResponse.setCustomerName(deliveryRequest.getCustomerName());
            deliveryResponse.setReceiveDate(deliveryRequest.getReceiveDate());
            deliveryResponse.setStaffName(staffAccount.getFirstName() + " " + staffAccount.getLastName());
            deliveryResponse.setRemainAmount(remainAmount);
            deliveryResponse.setHealthKoiDescription(deliveryRequest.getHealthKoiDescription());
            deliveryResponse.setBookingId(bookingId);
            deliveryResponse.setReason(deliveryRequest.getReason());
            deliveryResponse.setStatus(deliveryRequest.getStatus());
            return deliveryResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeliveryResponse updateDeliveryHistory(Long bookingId, DeliveryRequest deliveryRequest) throws Exception {
        Bookings bookings = bookingRepository.findBookingsById(bookingId);
        Delivery delivery = deliveriesRepository.findDeliveryByBooking(bookings);
        if(delivery == null) {
            throw new NotFoundEntity("Delivery not found");
        }
        delivery.setCustomerName(deliveryRequest.getCustomerName());
        delivery.setReceiveDate(deliveryRequest.getReceiveDate());
        delivery.setHealthKoiDescription(deliveryRequest.getHealthKoiDescription());
        if(deliveryRequest.getStatus()== DeliveryStatus.CANCELLED){
            if(deliveryRequest.getReason().isEmpty())
                throw new NotFoundEntity("CANCELLED must have a reason!");
        }
        delivery.setReason(deliveryRequest.getReason());
        delivery.setStatus(deliveryRequest.getStatus());
        deliveriesRepository.save(delivery);
        DeliveryResponse deliveryResponse = new DeliveryResponse();
        deliveryResponse.setCustomerName(deliveryRequest.getCustomerName());
        deliveryResponse.setReceiveDate(deliveryRequest.getReceiveDate());
        deliveryResponse.setStaffName(delivery.getDeliveryStaff().getFirstName() + " " + delivery.getDeliveryStaff().getLastName());
        deliveryResponse.setRemainAmount(delivery.getRemainAmount());
        deliveryResponse.setHealthKoiDescription(deliveryRequest.getHealthKoiDescription());
        deliveryResponse.setBookingId(delivery.getBooking().getId());
        deliveryResponse.setStatus(deliveryRequest.getStatus());
        return deliveryResponse;

    }

    @Override
    public void deleteDelivery(Long deliveryId) {
        Delivery delivery = deliveriesRepository.findDeliveriesById(deliveryId);
        if (delivery == null) {
            throw new NotFoundEntity("Delivery not found");
        }
        deliveriesRepository.delete(delivery);
    }

    @Override
    public DeliveryResponse getDelivery(Long bookingId) {
        Bookings bookings = bookingRepository.findBookingsById(bookingId);
        Delivery delivery = deliveriesRepository.findDeliveryByBooking(bookings);
        if (delivery == null) {
            throw new NotFoundEntity("Delivery not found");
        }
        DeliveryResponse deliveryResponse = new DeliveryResponse();
        deliveryResponse.setReason(delivery.getReason());
        deliveryResponse.setCustomerName(delivery.getCustomerName());
        deliveryResponse.setReceiveDate(delivery.getReceiveDate());
        deliveryResponse.setStaffName(delivery.getDeliveryStaff().getFirstName() + " " + delivery.getDeliveryStaff().getLastName());
        deliveryResponse.setRemainAmount(delivery.getRemainAmount());
        deliveryResponse.setHealthKoiDescription(delivery.getHealthKoiDescription());
        deliveryResponse.setBookingId(delivery.getBooking().getId());
        deliveryResponse.setStatus(delivery.getStatus());
        return deliveryResponse;
    }

    @Override
    public List<DeliveryResponse> getAllDeliveries() {
        List<DeliveryResponse> deliveryResponses = new ArrayList<>();
        List<Delivery> deliveryList = deliveriesRepository.findAll();
        for (Delivery delivery : deliveryList) {
            DeliveryResponse deliveryResponse = new DeliveryResponse();
            deliveryResponse.setCustomerName(delivery.getCustomerName());
            deliveryResponse.setReceiveDate(delivery.getReceiveDate());
            deliveryResponse.setHealthKoiDescription(delivery.getHealthKoiDescription());
            deliveryResponse.setRemainAmount(delivery.getRemainAmount());
            deliveryResponse.setBookingId(delivery.getBooking().getId());
            deliveryResponse.setStaffName(delivery.getDeliveryStaff().getFirstName() + " " + delivery.getDeliveryStaff().getLastName());
            deliveryResponse.setStatus(delivery.getStatus());
            deliveryResponse.setReason(delivery.getReason());
            deliveryResponses.add(deliveryResponse);
        }
        return deliveryResponses;
    }
}
