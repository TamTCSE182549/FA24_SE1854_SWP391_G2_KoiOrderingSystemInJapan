package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.DepositRequest;
import fall24.swp391.KoiOrderingSystem.model.response.DepositRespone;
import fall24.swp391.KoiOrderingSystem.pojo.Deposit;

import java.util.List;

public interface IDepositService {

    DepositRespone getDepositByBookingId(Long bookingId);

    DepositRespone createDeposit(DepositRequest depositRequest, Long bookingId);

    DepositRespone deleteById(Long theid);

    DepositRespone updateDeposit(Long id, DepositRequest depositRequest);

    List<DepositRespone> getAllDeposit();

}
