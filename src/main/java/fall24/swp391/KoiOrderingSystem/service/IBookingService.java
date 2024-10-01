package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Bookings;

import java.util.List;
import java.util.Optional;

public interface IBookingService {

    // Create a new booking with default status as 'pending'
    Bookings createBooking(Bookings booking);

    // Read a booking by ID
    Optional<Bookings> getBookingById(Long id);

    // Read all bookings
    List<Bookings> getAllBookings();

    // Update an existing booking (update status if not pending or cancelled)
    Bookings updateBooking(Long id, Bookings bookingDetails);

    // Delete a booking by ID (update status to 'cancelled' and return a boolean)
    boolean deleteBooking(Long id);
}
