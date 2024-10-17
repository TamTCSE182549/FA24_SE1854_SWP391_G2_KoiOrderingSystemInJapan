package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IKoiFarmsRepository extends JpaRepository<KoiFarms, Long>{

    KoiFarms findKoiFarmsById(Long id);

    List<KoiFarms> findFarmById(Long id);

    @Query(value = "select * from koi_farms where is_active = '1' ", nativeQuery = true)
    List<KoiFarms> findKoiFarmIsActive();
}
