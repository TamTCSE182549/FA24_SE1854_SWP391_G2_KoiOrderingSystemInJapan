package fall24.swp391.KoiOrderingSystem.service;



import fall24.swp391.KoiOrderingSystem.model.request.BookingKoiDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.request.UpdateBookingKoiDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.BookingKoiDetailResponse;
import fall24.swp391.KoiOrderingSystem.pojo.BookingKoiDetail;

import java.util.List;

public interface IBookingKoiDetailService {

    BookingKoiDetailResponse createKoiDetail(BookingKoiDetailRequest bookingKoiDetailRequest, Long bookingId);

    void deletebyBookingKoiDetail(Long bookingKoiDetailId);

    List<BookingKoiDetail> bookingKoiDetails(Long bookingID);

    List<BookingKoiDetail> updateBookingKoiDetail(Long bookingId, List<UpdateBookingKoiDetailRequest> updateBookingKoiDetailRequest);
}
