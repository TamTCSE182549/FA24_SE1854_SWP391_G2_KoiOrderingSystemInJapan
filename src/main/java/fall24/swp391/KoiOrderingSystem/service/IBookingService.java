package fall24.swp391.KoiOrderingSystem.service;


import fall24.swp391.KoiOrderingSystem.model.request.BookingKoiRequest;
//import fall24.swp391.KoiOrderingSystem.model.request.BookingRequest;
//import fall24.swp391.KoiOrderingSystem.model.response.BookingResponse;
import fall24.swp391.KoiOrderingSystem.model.request.BookingTourRequest;
import fall24.swp391.KoiOrderingSystem.model.request.BookingUpdateRequestCus;
import fall24.swp391.KoiOrderingSystem.model.request.BookingUpdateRequestStaff;
import fall24.swp391.KoiOrderingSystem.model.response.BookingTourResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;

import java.util.List;

public interface IBookingService {

    // Create a new booking with default status as 'pending'
    BookingTourResponse createTourBooking(BookingTourRequest bookingTourRequest);

    // Get tour bookings by id for cus
//    List<Bookings> getTourBooking(Long idAccount);

    //View booking of tour for STAFF
    List<BookingTourResponse> bookingForTour();

    //Get tour booking response by accountID for customer
    List<BookingTourResponse> getTourBookingResponse();

    // Update an existing booking (update status if not pending or cancelled)
//    Bookings updateTourBooking(Long id, Bookings bookingDetails);

    BookingTourResponse updateTourBookingResponse(Bookings bookingDetails);

    BookingTourResponse bookingUpdatePaymentMethod(BookingUpdateRequestCus bookingUpdateRequestCus);

    BookingTourResponse responseUpdateForStaff(BookingUpdateRequestStaff bookingUpdateRequestStaff);

    // Delete a booking by ID (update status to 'cancelled' and return a boolean)
    void deleteBookingForManager(Long id);

    BookingTourResponse createKoiBooking(BookingKoiRequest bookingKoiRequest) throws Exception;

    Bookings updateKoiBooking(Long id, Bookings bookingDetails);

    List<Bookings> getKoiBooking(Long accountID);

    BookingTourResponse deleteBookingResponse(Long bookingID);

}
