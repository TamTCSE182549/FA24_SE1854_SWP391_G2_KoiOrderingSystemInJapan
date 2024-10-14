package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.BookingType;
import fall24.swp391.KoiOrderingSystem.enums.DepositStatus;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
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
import java.util.Optional;
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
    public Deposit createDeposit(DepositRequest depositRequest, Long bookingId) {
        try{
            Bookings booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
            Deposit deposit =modelMapper.map(depositRequest,Deposit.class);
            deposit.setDepositStatus(DepositStatus.processing);
            deposit.setBooking(booking);
            deposit.setDepositAmount(booking.getTotalAmountWithVAT()*depositRequest.getDepositPercentage());
            deposit.setRemainAmount(booking.getTotalAmountWithVAT()-deposit.getDepositAmount());
            return depositRepository.save(deposit);
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
                DepositRespone depositRespone = modelMapper.map(deposit, DepositRespone.class);
                depositRepository.save(deposit);
                return depositRespone;
        }catch (Exception e){
            throw new NotDeleteException(e.getMessage());
        }
    }


    @Override
    public Deposit updateDeposit(Long id, DepositRequest depositRequest) {
        Deposit Deposit = depositRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deposit Id not found"));

        if (Deposit.getDepositStatus() == DepositStatus.cancelled) {
            throw new NotUpdateException("Cannot update the cancelled deposit");
        }
        if (Deposit.getDepositStatus() == DepositStatus.processing) {
            Deposit.setDepositStatus(DepositStatus.complete);
        }

            Deposit.setDeliveryExpectedDate(depositRequest.getDeliveryExpectedDate());

            Deposit.setShippingAddress(depositRequest.getShippingAddress());

            Deposit.setShippingFee(depositRequest.getShippingFee());

            Deposit.setDepositPercentage(depositRequest.getDepositPercentage());

            Deposit.setDepositAmount(depositRequest.getDepositPercentage()*Deposit.getBooking().getTotalAmountWithVAT());

            Deposit.setRemainAmount(Deposit.getBooking().getTotalAmountWithVAT()-Deposit.getDepositAmount());

            Bookings relateBooking = Deposit.getBooking();
            if (relateBooking != null) {
                if (relateBooking.getBookingType() == BookingType.BookingForKoi) {
                    relateBooking.setPaymentStatus(PaymentStatus.shipped);
                    bookingRepository.save(relateBooking);
                }
            }
            return depositRepository.save(Deposit);
    }
}
