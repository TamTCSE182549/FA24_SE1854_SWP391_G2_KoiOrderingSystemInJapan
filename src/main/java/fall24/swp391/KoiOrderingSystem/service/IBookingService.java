package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.BookingTourRequest;
import fall24.swp391.KoiOrderingSystem.model.response.BookingTourResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;

import java.util.List;

public interface IBookingService {

    // Create a new booking with default status as 'pending'
    BookingTourResponse createTourBooking(BookingTourRequest bookingTourRequest) throws Exception;

    // Get tour bookings
    List<Bookings> getTourBooking(Long idAccount);

    //Get tour booking response by accountID
    List<BookingTourResponse> getTourBookingResponse(Long idAccount);

    // Update an existing booking (update status if not pending or cancelled)
    Bookings updateTourBooking(Long id, Bookings bookingDetails);

    BookingTourResponse updateTourBookingResponse(Bookings bookingDetails);

    // Delete a booking by ID (update status to 'cancelled' and return a boolean)
    Bookings deleteBooking(Long id);

    BookingTourResponse deleteBookingResponse(Long bookingID);
}
