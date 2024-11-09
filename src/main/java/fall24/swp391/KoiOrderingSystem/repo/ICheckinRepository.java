package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICheckinRepository extends JpaRepository<Checkin, Long> {
    List<Checkin> findByBookingTour(Bookings bookings);

    @Query(value = "Select * from checkin where customer_id = ?1 order by id desc", nativeQuery = true)
    List<Checkin> findByCustomerId(Long customerId);

    @Query(value = "Select * from checkin order by id desc", nativeQuery = true)
    List<Checkin> findAllByOrderByIdDesc();
}
