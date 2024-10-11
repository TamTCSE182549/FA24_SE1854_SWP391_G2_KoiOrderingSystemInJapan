package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.BookingType;
import fall24.swp391.KoiOrderingSystem.enums.DepositStatus;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Deposit;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IDepositRepository;
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

    @Override
    public List<Deposit> getDepositByBookingId(Long bookingId) {
        List<Deposit> depositList = depositRepository.findByBookingId(bookingId);
        return depositList;
    }

    @Override
    public Deposit createDeposit(Deposit theDeposit, Long bookingId) {
        Bookings booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        theDeposit.setDepositStatus(DepositStatus.processing);
        theDeposit.setBooking(booking);
        theDeposit.setDepositAmount(booking.getTotalAmountWithVAT()*theDeposit.getDepositpercentage());
        theDeposit.setRemainAmount(booking.getTotalAmountWithVAT()-theDeposit.getDepositAmount());
        return depositRepository.save(theDeposit);
    }

    @Override
    public Boolean deleteById(Long theid) {
        Optional<Deposit> deposit = depositRepository.findById(theid);
        if(deposit.isPresent()){
            Deposit depositDelete = deposit.get();
            depositDelete.setDepositStatus(DepositStatus.cancelled);
            depositRepository.save(depositDelete);
            return true;
        }
        return false ;
    }

    @Override
    public Deposit updateDeposit(Long id, Deposit depositDeatil) {
        Optional<Deposit> existingDeposit = depositRepository.findById(id);
        Deposit depositUpdate = null;
        if (existingDeposit.isPresent()) {
            depositUpdate = existingDeposit.get();

            if (depositUpdate.getDepositStatus() == DepositStatus.processing) {
                depositUpdate.setDepositStatus(DepositStatus.complete);

                depositUpdate.setDepositAmount(depositDeatil.getDepositAmount());

                depositUpdate.setDepositDate(depositDeatil.getDepositDate());

                depositUpdate.setRemainAmount(depositDeatil.getDepositAmount());

                depositUpdate.setDeliveryExpectedDate(depositDeatil.getDeliveryExpectedDate());

                depositUpdate.setShippingAddress(depositDeatil.getShippingAddress());

                depositUpdate.setShippingFee(depositDeatil.getShippingFee());


                Bookings relateBooking = depositUpdate.getBooking();
                if (relateBooking != null) {
                    if (relateBooking.getBookingType() == BookingType.BookingForKoi) {
                        relateBooking.setPaymentStatus(PaymentStatus.shipped);
                        bookingRepository.save(relateBooking);
                    }
                }

                return depositRepository.save(depositUpdate);
            }
            return depositUpdate;
        }
        return null;
    }
}
