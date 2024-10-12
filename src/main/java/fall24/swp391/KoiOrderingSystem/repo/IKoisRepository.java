package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IKoisRepository extends JpaRepository<Kois, Long>{

    // Tìm tất cả Kois thuộc một KoiFarm cụ thể
    List<Kois> findByKoiOfFarmList_KoiFarms_Id(Long farmId);
}
