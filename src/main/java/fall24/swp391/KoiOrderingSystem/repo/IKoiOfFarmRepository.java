package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IKoiOfFarmRepository extends JpaRepository<KoiOfFarm, Long> {
}