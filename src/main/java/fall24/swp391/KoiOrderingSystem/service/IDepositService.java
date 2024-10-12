package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.DepositRequest;
import fall24.swp391.KoiOrderingSystem.model.response.DepositRespone;
import fall24.swp391.KoiOrderingSystem.pojo.Deposit;

import java.util.List;

public interface IDepositService {

    List<DepositRespone> getDepositByBookingId(Long bookingId);

    Deposit createDeposit(DepositRequest depositRequest, Long bookingId);

    DepositRespone deleteById(Long theid);

    Deposit updateDeposit(Long id, DepositRequest depositRequest);
}
