package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IKoiOfFarmRepository extends JpaRepository<KoiOfFarm, Long> {

    List<KoiOfFarm> findKoiOfFarmBykoiFarms(KoiFarms koiFarms);

    List<KoiOfFarm> findByKoisId(Long koiId);

    @Query("SELECT kf FROM KoiOfFarm kf WHERE kf.koiFarms.id = :farmId AND kf.kois.id = :koiId")
    KoiOfFarm findByFarmIdAndKoiId(@Param("farmId") Long farmId, @Param("koiId") Long koiId);
}
