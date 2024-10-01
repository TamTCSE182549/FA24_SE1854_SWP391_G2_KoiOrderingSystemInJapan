package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriesRepository extends JpaRepository<Categories, Long> {
}
