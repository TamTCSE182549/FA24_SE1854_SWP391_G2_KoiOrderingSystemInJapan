package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingTourDetailRepository extends JpaRepository<BookingTourDetail, Long> {

    @Query(value = "select btd.* from booking_tour_detail as btd where booking_id = ?1", nativeQuery = true)
    List<BookingTourDetail> showDetailOfBookingID(Long bookingID);

    @Query(value = "select btd.* from booking_tour_detail as btd where booking_id = ?1", nativeQuery = true)
    BookingTourDetail showDetailOfBookingIDOne(Long bookingID);

    @Query(value = "select btd.total_amount from booking_tour_detail as btd where btd.booking_id = :bookingID", nativeQuery = true)
    Float findTotalAmount(@Param("bookingID") Long bookingID);//bookingID: cho phép truyền giá trị ? chưa rõ

    @Query(value = "delete from booking_tour_detail where booking_id = ?1", nativeQuery = true)
    void deleteBTDByBooking_Id(Long id);


}
