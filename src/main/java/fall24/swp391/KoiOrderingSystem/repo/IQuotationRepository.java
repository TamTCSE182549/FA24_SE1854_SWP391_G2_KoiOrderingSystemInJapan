package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Quotations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IQuotationRepository extends JpaRepository<Quotations, Long> {
}
