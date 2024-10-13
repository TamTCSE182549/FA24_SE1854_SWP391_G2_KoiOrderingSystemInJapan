package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.BookingTourDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.BookingTourDetailResponse;
import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;

import java.util.List;

public interface IBookingTourDetailService {
    List<BookingTourDetailResponse> bookingTourDetails(Long bookingID);

//    List<BookingTourDetailResponse> bookingTourDetailRes(Long bookingID);

//    List<BookingTourDetail> getAll();

//    BookingTourDetail createBookingTourDetail(BookingTourDetail bookingTourDetail);

    BookingTourDetailResponse createBookingTourDetailRes(BookingTourDetailRequest bookingTourDetailRequest);

    void deleteBookingTourDetail(Long bookingTourDetailID);

    BookingTourDetail updateBookingTourDetail(BookingTourDetail bookingTourDetail);
}
