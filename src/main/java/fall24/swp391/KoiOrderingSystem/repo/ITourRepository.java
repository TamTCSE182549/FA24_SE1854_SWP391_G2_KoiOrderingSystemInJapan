package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.model.response.TourResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITourRepository extends JpaRepository<Tours, Long> {
    @Query(value = "select * from tours where status = 'active'", nativeQuery = true)
    List<Tours> findAllByStatusActive();

    @Query(value = "SELECT * FROM Tours", nativeQuery = true)
    Page<Tours> showAllPageable(Pageable pageable);

    @Query(value = "select * from tours where tour_name like %?1%", nativeQuery = true)
    Page<Tours> showTourByName(String nameTour, Pageable pageable);

    @Query(value = "select f.* from tours t, kois k, koi_of_farm kf, categories c, koi_farms f where c.id = ?1 and c.id = koi.category_id and koi.id = kf.koi_id and kf.farm_id = f.id", nativeQuery = true)
    Page<Tours> findTourByKoiCategory(Integer categoryId, Pageable pageable);

    @Query(value = "select distinct t.* " +
            "from tours t, tour_detail td, koi_farms kf, koi_of_farm kof, kois k " +
            "where k.koi_name like '%?1%' and t.id = td.tour_id and td.farm_id = kf.id and kf.id = kof.farm_id and kof.koi_id = k.id", nativeQuery = true)
    Page<Tours> findTourByKoiName(String koiName, Pageable pageable);
}
