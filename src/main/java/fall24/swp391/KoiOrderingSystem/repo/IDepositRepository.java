package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepositRepository extends JpaRepository <Deposit,Long> {
}
