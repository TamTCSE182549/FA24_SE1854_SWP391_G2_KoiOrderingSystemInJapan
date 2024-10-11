package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.BookingKoiDetail;
import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingKoiDetailRepository extends JpaRepository<BookingKoiDetail, Long> {

    @Query(value = "select btd.* from booking_koi_detail as btd where booking_id = ?1", nativeQuery = true)
    List<BookingKoiDetail> showDetailOfBookingID(Long bookingID);

    @Query(value = "select btd.total_amount from booking_koi_detail as btd where bkd.booking_id = :bookingID", nativeQuery = true)
    Float findTotalAmount(@Param("bookingID") Long bookingID);
}
