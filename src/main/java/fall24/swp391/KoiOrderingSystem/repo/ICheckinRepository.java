package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICheckinRepository extends JpaRepository<Checkin, Long> {
}
