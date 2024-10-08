package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingTourDetailRepository extends JpaRepository<BookingTourDetail, Long> {

    @Query(value = "select btd.* from booking_tour_detail as btd where booking_id = ?1", nativeQuery = true)
    List<BookingTourDetail> showDetailOfBookingID(Long bookingID);
}
