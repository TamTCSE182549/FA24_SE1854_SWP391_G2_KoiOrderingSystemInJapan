package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Deposit;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDepositRepository extends JpaRepository <Deposit,Long> {
    List<Deposit> findByBookingId(Long bookingId);

    @Query(value = "select * from deposit where deposit_status = 'processing' or deposit_status = 'complete'", nativeQuery = true)
    List<Deposit> findAllByStatusActive();
}
