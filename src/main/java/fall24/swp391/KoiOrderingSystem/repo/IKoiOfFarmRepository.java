package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IKoiOfFarmRepository extends JpaRepository<KoiOfFarm, Long> {

    List<KoiOfFarm> findKoiOfFarmBykoiFarms(KoiFarms koiFarms);

    List<KoiOfFarm> findByKoisId(Long koiId);
}
