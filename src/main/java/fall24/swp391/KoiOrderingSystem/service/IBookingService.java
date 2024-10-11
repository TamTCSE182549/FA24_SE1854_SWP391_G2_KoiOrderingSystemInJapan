package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.BookingKoiRequest;
import fall24.swp391.KoiOrderingSystem.model.request.BookingRequest;
import fall24.swp391.KoiOrderingSystem.model.response.BookingResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;

import java.util.List;

public interface IBookingService {

    // Create a new booking with default status as 'pending'
    BookingResponse createTourBooking(BookingRequest bookingRequest) throws Exception;

    // Get tour bookings
    List<Bookings> getTourBooking(Long idAccount);

    // Update an existing booking (update status if not pending or cancelled)
    Bookings updateTourBooking(Long id, Bookings bookingDetails) ;

    // Delete a booking by ID (update status to 'cancelled' and return a boolean)
    Bookings deleteBooking(Long id);

    BookingResponse createKoiBooking(BookingKoiRequest bookingKoiRequest) throws Exception;

    Bookings updateKoiBooking(Long id, Bookings bookingDetails);

    List<Bookings> getKoiBooking(Long accountID);
}
