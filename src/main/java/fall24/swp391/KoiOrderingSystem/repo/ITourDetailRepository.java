package fall24.swp391.KoiOrderingSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITourDetailRepository extends JpaRepository<ITourRepository, Long> {
}
