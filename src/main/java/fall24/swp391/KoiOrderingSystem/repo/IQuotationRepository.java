package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Quotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuotationRepository extends JpaRepository<Quotations, Long> {

@Query("SELECT q FROM Quotations q WHERE q.booking.id = :bookingId")
List<Quotations> findByBookingId(long bookingId);

    Quotations findQuotationsById(Long quotationId);

    @Query(value = "SELECT * FROM Quotations order by id DESC", nativeQuery = true)
    Page<Quotations> showAllPageable(Pageable pageable);

    @Query(value = "SELECT * FROM quotations order by id DESC", nativeQuery = true)
    List<Quotations> getAllQuotation();
}
