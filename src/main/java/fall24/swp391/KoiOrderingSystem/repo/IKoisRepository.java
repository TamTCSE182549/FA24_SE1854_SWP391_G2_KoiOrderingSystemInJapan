package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IKoisRepository extends JpaRepository<Kois, Long>{
}
