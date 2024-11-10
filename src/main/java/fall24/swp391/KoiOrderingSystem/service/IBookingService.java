package fall24.swp391.KoiOrderingSystem.service;


import fall24.swp391.KoiOrderingSystem.model.request.*;
//import fall24.swp391.KoiOrderingSystem.model.request.BookingRequest;
//import fall24.swp391.KoiOrderingSystem.model.response.BookingResponse;

import fall24.swp391.KoiOrderingSystem.model.response.BookingResponseDetail;

import fall24.swp391.KoiOrderingSystem.model.response.BookingTourRes;
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

    List<BookingTourResponse> bookingForTourAccepted();

    void acceptBooking(Long bookingID);

    //Get tour booking response by accountID for customer
    List<BookingTourResponse> getTourBookingResponse();

    List<BookingTourResponse> getBookingResponseForDashBoard();

    List<BookingTourResponse> getBookingResponseComplete();

    // Update an existing booking (update status if not pending or cancelled)
//    Bookings updateTourBooking(Long id, Bookings bookingDetails);

    BookingTourResponse updateTourBookingResponse(Bookings bookingDetails);

    BookingTourResponse bookingUpdatePaymentMethod(BookingUpdateRequestCus bookingUpdateRequestCus);

    BookingTourResponse responseUpdateForStaff(BookingUpdateRequestStaff bookingUpdateRequestStaff);

    // Delete a booking by ID (update status to 'cancelled' and return a boolean)
    void deleteBookingForManager(Long id);

    BookingTourResponse createKoiBooking(BookingKoiRequest bookingKoiRequest,Long bookingId) ;

    BookingTourResponse updateKoiBooking(Long id, BookingUpdate bookingUpdate);

    List<BookingResponseDetail> getKoiBookingById();

    List<BookingResponseDetail> getKoiBooking();

    BookingResponseDetail viewDetailBooking(Long bookingId);

    BookingTourResponse deleteBookingResponse(Long bookingID);

    BookingResponseDetail deleteBooking(Long bookingId);

    BookingTourResponse updateStatus(Long bookingId);

    String createUrl(Long bookingId) throws  Exception;

    void updatePayment(PaymentRequest paymentRequest);

    BookingTourRes getBookingById(Long Id);

    List<BookingTourResponse> getKoiBookingShipping();

    List<BookingTourResponse> getAllBooking();

    List<BookingTourResponse> showAllBookingStatus(String paymentStatus);

    List<BookingTourResponse> showBookingTourStatus(String paymentStatus);

    List<BookingTourResponse> showBookingKoiStatus(String paymentStatus);
}
