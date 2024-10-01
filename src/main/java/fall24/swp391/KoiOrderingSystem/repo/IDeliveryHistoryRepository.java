package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.DeliveryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDeliveryHistoryRepository extends JpaRepository<DeliveryHistory, Long> {
}
