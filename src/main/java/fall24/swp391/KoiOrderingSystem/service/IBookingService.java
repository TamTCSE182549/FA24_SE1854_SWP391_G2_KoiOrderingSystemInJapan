package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Bookings;

import java.util.List;

public interface IBookingService {

    // Create a new booking with default status as 'pending'
    Bookings createBooking(Bookings booking);

    // Get tour bookings
    List<Bookings> getTourBooking(Long idAccount);

    // Update an existing booking (update status if not pending or cancelled)
    void updateBooking(Long id, Bookings bookingDetails) ;

    // Delete a booking by ID (update status to 'cancelled' and return a boolean)
    Bookings deleteBooking(Long id);

    //Create a payment for a Tour Pending for User
    Bookings createPaymentTourPending(Bookings booking);

    Bookings cancelledPaymentTour(Bookings booking);

}
