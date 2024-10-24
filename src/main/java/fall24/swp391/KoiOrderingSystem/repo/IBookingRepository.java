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
            "where b.booking_type = 'BookingForTour' and b.account_id = ?1 and b.payment_status!='cancelled'", nativeQuery = true)
    List<Bookings> listTourBookingByID(Long idAccount);

    @Query(value = "select b.* " +
            "from bookings b " +
            "where b.booking_type = 'BookingForTour'", nativeQuery = true)
    List<Bookings> listBookingForTour();


    //list booking type 2
    @Query(value = "select b.* " +
            "from bookings b " +
            "where  b.booking_type = 'BookingForKoi'  and b.account_id = ?1", nativeQuery = true)
    List<Bookings> listKoiBooking(Long idAccount);

    @Query(value = "select b.* " +
            "from bookings b " +
            "where b.booking_type = 'BookingForKoi'", nativeQuery = true)
    List<Bookings> listBookingForKoi();


    Bookings findBookingsById(Long id);
}
