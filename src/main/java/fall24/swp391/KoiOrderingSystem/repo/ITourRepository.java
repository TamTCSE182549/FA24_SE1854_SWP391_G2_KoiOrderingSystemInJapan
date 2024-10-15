package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITourRepository extends JpaRepository<Tours, Long> {
    @Query(value = "select * from tours where status = 'active'", nativeQuery = true)
    List<Tours> findAllByStatusActive();

    @Query(value = "SELECT * FROM Tours", nativeQuery = true)
    Page<Tours> showAllPageable(Pageable pageable);

    @Query(value = "select * from tours where tour_name like %?1%", nativeQuery = true)
    Page<Tours> showTourByName(String nameTour, Pageable pageable);
}
