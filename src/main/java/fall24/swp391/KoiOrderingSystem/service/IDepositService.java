package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Deposit;

import java.util.List;

public interface IDepositService {

    List<Deposit> findAll();

    Deposit findById(Long Id);

    Deposit save(Deposit theDeposit);

    void deleteById(Long theid);
}
