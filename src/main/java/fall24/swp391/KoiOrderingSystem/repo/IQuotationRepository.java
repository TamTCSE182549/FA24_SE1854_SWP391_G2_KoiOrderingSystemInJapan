package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Quotations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuotationRepository extends JpaRepository<Quotations, Long> {

    @Query(value = "select qt.* from quotations as qt where id = ?", nativeQuery = true)
    List<Quotations> listQuotations(Long bookId);
}
