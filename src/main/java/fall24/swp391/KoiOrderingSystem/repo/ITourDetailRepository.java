package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.pojo.TourDetail;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ITourDetailRepository extends JpaRepository<TourDetail, Long> {

    @Query(name = "select * from tour_detail where farm_id = ?1", nativeQuery = true)
    List<TourDetail> findByFarm_Id(Long farmID);

    @Query(name = "select * from tour_detail where tour_id = ?1", nativeQuery = true)
    List<TourDetail> findByTour_Id(Long tourID);

    TourDetail findByFarmAndTour(KoiFarms farm, Tours tour);
}
