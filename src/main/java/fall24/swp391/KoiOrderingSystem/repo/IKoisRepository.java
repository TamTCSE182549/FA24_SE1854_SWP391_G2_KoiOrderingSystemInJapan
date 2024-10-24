package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IKoisRepository extends JpaRepository<Kois, Long>{

    Kois findKoisById(Long id);
    @Query("select p.koiName, sum(bkd.quantity) as totalSold from BookingKoiDetail bkd Join bkd.koi p" +
            " group by p.id" +
            " order by totalSold desc" +
            " limit 5")
    List<Object[]> findTop5BestSellingKoi();

    List<Kois> findByIsActiveTrue();
}
