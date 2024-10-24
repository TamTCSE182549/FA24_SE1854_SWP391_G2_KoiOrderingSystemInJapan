package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmImage;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IKoiFarmImageRepository extends JpaRepository<KoiFarmImage, Long>{
    void deleteByKoiFarms(KoiFarms koiFarms);
}
