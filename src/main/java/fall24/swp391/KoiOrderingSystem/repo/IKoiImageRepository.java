package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.KoiImage;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IKoiImageRepository extends JpaRepository<KoiImage, Long> {
    void deleteById(Long id);
    List<KoiImage> findKoiImageByKois(Kois kois);

    void deleteByKois(Kois kois);
}
