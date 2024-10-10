package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.DepositStatus;
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
        if(existingDeposit.isPresent()){
            Deposit depositUpdate = existingDeposit.get();

            if(depositUpdate.getDepositStatus() != DepositStatus.processing){
                depositUpdate.setDepositStatus(depositDeatil.getDepositStatus());
            }
            depositUpdate.setDepositAmount(depositDeatil.getDepositAmount());

            depositUpdate.setDepositDate(depositDeatil.getDepositDate());

            depositUpdate.setRemainAmount(depositDeatil.getDepositAmount());

            depositUpdate.setDeliveryExpectedDate(depositDeatil.getDeliveryExpectedDate());

            depositUpdate.setShippingAddress(depositDeatil.getShippingAddress());

            depositUpdate.setShippingFee(depositDeatil.getShippingFee());

            return depositRepository.save(depositUpdate);
        }
        return null;
    }
}
