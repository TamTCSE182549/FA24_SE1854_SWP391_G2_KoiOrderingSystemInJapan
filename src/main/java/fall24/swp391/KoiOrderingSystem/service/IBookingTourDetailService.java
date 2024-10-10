package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;

import java.util.List;

public interface IBookingTourDetailService {
    List<BookingTourDetail> bookingTourDetails(Long bookingID);

    List<BookingTourDetail> getAll();

    BookingTourDetail createBookingTourDetail(BookingTourDetail bookingTourDetail);

    void deleteBookingTourDetail(Long bookingTourDetailID);

    BookingTourDetail updateBookingTourDetail(BookingTourDetail bookingTourDetail);
}
