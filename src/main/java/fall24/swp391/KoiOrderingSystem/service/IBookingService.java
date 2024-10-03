package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IBookingService {

    // Create a new booking with default status as 'pending'
    Bookings createBooking(Bookings booking);

    // Get tour bookings
    List<Bookings> getTourBooking(Long idAccount);

    // Update an existing booking (update status if not pending or cancelled)
    Bookings updateBooking(Long id, Bookings bookingDetails);

    // Delete a booking by ID (update status to 'cancelled' and return a boolean)
    boolean deleteBooking(Long id);
}
