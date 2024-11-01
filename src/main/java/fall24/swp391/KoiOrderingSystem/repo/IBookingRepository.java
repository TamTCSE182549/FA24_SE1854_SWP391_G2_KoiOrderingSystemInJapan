package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookingRepository extends JpaRepository<Bookings, Long> {

    //list booking type 1
    @Query(value = "select b.* " +
            "from bookings b " +
            "where b.booking_type = 'BookingForTour' and b.account_id = ?1 and b.payment_status != 'cancelled'", nativeQuery = true)
    List<Bookings> listTourBookingByID(Long idAccount);

    @Query(value = "select b.* " +
            "from bookings b " +
            "where b.booking_type = 'BookingForTour' and b.account_id = ?1 and b.payment_status != 'complete' and b.payment_status != 'cancelled'", nativeQuery = true)
    List<Bookings> listTourBookingByIDOtherStatus(Long idAccount);

    @Query(value = "select b.* " +
            "from bookings b " +
            "where b.booking_type = 'BookingForTour'", nativeQuery = true)
    List<Bookings> listBookingForTour();

    @Query(value = "select b.* " +
            "from bookings b " +
            "where b.payment_status = 'complete'", nativeQuery = true)
    List<Bookings> listBookingForDashBoard();

    //list booking type 2
    @Query(value = "select b.* " +
            "from bookings b " +
            "where  b.booking_type = 'BookingForKoi'  and b.account_id = ?1", nativeQuery = true)
    List<Bookings> listKoiBooking(Long idAccount);

    @Query(value = "select b.* " +
            "from bookings b " +
            "where b.booking_type = 'BookingForKoi'", nativeQuery = true)
    List<Bookings> listBookingForKoi();


    @Query(value = "select b.* " +
            "from bookings b " +
            "where b.booking_type = 'BookingForKoi' and b.payment_status = 'shipped'", nativeQuery = true)
    List<Bookings> listKoiBookingShipping();

    @Query("select YEAR(b.paymentDate) as year, MONTH(b.paymentDate) as month, sum(b.totalAmountWithVAT) from Bookings b"+
            " where b.paymentStatus = 'complete' " +
            "GROUP BY YEAR(b.paymentDate),MONTH(b.paymentDate)" +
            "ORDER BY YEAR(b.paymentDate),MONTH(b.paymentDate)")
    List<Object[]> calculatingTotalAmountWithVAT();

    Bookings findBookingsById(Long id);

    @Query(value = "select b.* " +
            "from bookings b " +
            "where b.payment_status = ?1", nativeQuery = true)
    List<Bookings> findBookingsByPaymentStatus(String paymentStatus);

    @Query(value = "select b.* " +
            "from bookings b " +
            "where b.booking_type = 'BookingForTour' and b.payment_status = ?1", nativeQuery = true)
    List<Bookings> findBookingForTourByPaymentStatus(String paymentStatus);

    @Query(value = "select b.* " +
            "from bookings b " +
            "where b.booking_type = 'BookingForKoi' and b.payment_status = ?1", nativeQuery = true)
    List<Bookings> findBookingForKoiByPaymentStatus(String paymentStatus);
}
