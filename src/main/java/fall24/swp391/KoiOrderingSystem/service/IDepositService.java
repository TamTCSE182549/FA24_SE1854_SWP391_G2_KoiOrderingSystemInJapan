package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Deposit;

import java.util.List;

public interface IDepositService {

    List<Deposit> getDepositByBookingId(Long bookingId);

    Deposit createDeposit(Deposit theDeposit);

    Boolean deleteById(Long theid);

    Deposit updateDeposit(Long id,Deposit deposit);
}
