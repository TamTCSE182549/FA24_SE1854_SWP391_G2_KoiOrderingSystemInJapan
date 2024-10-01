package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Deposit;
import fall24.swp391.KoiOrderingSystem.repo.IDepositRepository;

import java.util.List;

public class DepositService implements IDepositService{

    private IDepositRepository depositRepository;

    public DepositService(IDepositRepository thedepositRepository) {
        depositRepository = thedepositRepository;
    }

    @Override
    public List<Deposit> findAll() {
        return depositRepository.findAll();
    }

    @Override
    public Deposit findById(Long Id) {
        return null;
    }

    @Override
    public Deposit save(Deposit theDeposit) {
        return depositRepository.save(theDeposit);
    }

    @Override
    public void deleteById(Long theid) {
        depositRepository.deleteById(theid);
    }
}
