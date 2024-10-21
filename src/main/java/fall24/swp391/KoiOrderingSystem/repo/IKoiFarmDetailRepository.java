package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IKoiFarmDetailRepository extends JpaRepository<KoiFarmDetail, Long> {

    @Query(name = "select * from koi_farm where farm)id = ?1", nativeQuery = true)
    List<KoiFarmDetail> findByFarm_Id(Long farmID);
}
