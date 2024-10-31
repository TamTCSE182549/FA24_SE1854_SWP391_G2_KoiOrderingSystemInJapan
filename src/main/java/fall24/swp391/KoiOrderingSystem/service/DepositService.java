package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.BookingType;
import fall24.swp391.KoiOrderingSystem.enums.DepositStatus;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotCreateException;
import fall24.swp391.KoiOrderingSystem.exception.NotDeleteException;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.model.request.DepositRequest;
import fall24.swp391.KoiOrderingSystem.model.response.DepositRespone;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Deposit;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IDepositRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepositService implements IDepositService{

    @Autowired
    private IDepositRepository depositRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DepositRespone> getDepositByBookingId(Long bookingId) {
        List<Deposit> depositList = depositRepository.findByBookingId(bookingId);
        return depositList.stream().map(deposit -> {
            DepositRespone depositRespone = modelMapper.map(deposit, DepositRespone.class);
            return depositRespone;
        }).toList();
    }


    @Override
    public DepositRespone createDeposit(DepositRequest depositRequest, Long bookingId) {
        try{
            Deposit deposit =modelMapper.map(depositRequest,Deposit.class);
            Bookings booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
            deposit.setDepositStatus(DepositStatus.complete);
            deposit.setBooking(booking);
            deposit.setDepositAmount(booking.getTotalAmountWithVAT()*depositRequest.getDepositPercentage());
            deposit.setRemainAmount(booking.getTotalAmountWithVAT()-deposit.getDepositAmount()+deposit.getShippingFee());
            Bookings relateBooking = deposit.getBooking();
            if (relateBooking != null) {
                if (relateBooking.getBookingType() == BookingType.BookingForKoi) {
                    relateBooking.setPaymentStatus(PaymentStatus.shipped);
                    bookingRepository.save(relateBooking);
                }
            }
            depositRepository.save(deposit);
            DepositRespone depositRespone = modelMapper.map(deposit, DepositRespone.class);
            return depositRespone;
        }catch (Exception e){
            throw new NotCreateException(e.getMessage());
        }
    }

    @Override
    public DepositRespone deleteById(Long theid) {
        try {
            Deposit deposit = depositRepository.findById(theid)
                    .orElseThrow(() ->new RuntimeException("Deposit Id not found"));
                deposit.setDepositStatus(DepositStatus.cancelled);
                depositRepository.save(deposit);
                DepositRespone depositRespone = modelMapper.map(deposit, DepositRespone.class);
                return depositRespone;
        }catch (Exception e){
            throw new NotDeleteException(e.getMessage());
        }
    }


    @Override
    public DepositRespone updateDeposit(Long id, DepositRequest depositRequest) {
        try{
        Deposit deposit = depositRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deposit Id not found"));

        if (deposit.getDepositStatus() == DepositStatus.cancelled) {
            throw new NotUpdateException("Cannot update the cancelled deposit");
        }
        else if (deposit.getDepositStatus() == DepositStatus.processing) {
            deposit.setDepositStatus(DepositStatus.complete);
        }
            deposit.setDeliveryExpectedDate(depositRequest.getDeliveryExpectedDate());

            deposit.setShippingAddress(depositRequest.getShippingAddress());

            deposit.setShippingFee(depositRequest.getShippingFee());

            deposit.setDepositPercentage(depositRequest.getDepositPercentage());

            deposit.setDepositAmount(depositRequest.getDepositPercentage()*deposit.getBooking().getTotalAmountWithVAT());

            deposit.setRemainAmount(deposit.getBooking().getTotalAmountWithVAT()-deposit.getDepositAmount()+deposit.getShippingFee());

            Bookings relateBooking = deposit.getBooking();
            if (relateBooking != null) {
                if (relateBooking.getBookingType() == BookingType.BookingForKoi) {
                    relateBooking.setPaymentStatus(PaymentStatus.shipped);
                    bookingRepository.save(relateBooking);
                }
            }
            depositRepository.save(deposit);
            DepositRespone depositRespone =modelMapper.map(deposit,DepositRespone.class);
            return depositRespone;
        }catch (Exception e){
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public List<DepositRespone> getDepositIsActive() {
        List<Deposit> depositList = depositRepository.findAllByStatusActive();
        if(depositList.isEmpty()){
            throw new GenericException("Not active Deposit found");
        }
        return depositList.stream()
                .map(deposit -> {
                    DepositRespone depositRespone =modelMapper.map(deposit,DepositRespone.class);
                    return  depositRespone;
                }).toList();
    }
}

