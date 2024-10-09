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
            "from bookings b,  booking_type bt " +
            "where b.account_id = ?1 and b.booking_type_id = bt.id and bt.id = 1", nativeQuery = true)
    List<Bookings> listTourBooking(Long idAccount);

    //list booking type 2
    @Query(value = "select b.* " +
            "from bookings b,  booking_type bt " +
            "where b.account_id = ?1 and b.booking_type_id = bt.id and bt.id = 2", nativeQuery = true)
    List<Bookings> listKoiBooking(Long idAccount);
   Bookings findBookingsById(Long bookingId);

}
