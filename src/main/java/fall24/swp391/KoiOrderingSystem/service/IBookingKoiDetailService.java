package fall24.swp391.KoiOrderingSystem.service;



import fall24.swp391.KoiOrderingSystem.pojo.BookingKoiDetail;

import java.util.List;

public interface IBookingKoiDetailService {

    List<BookingKoiDetail> findAll();

    BookingKoiDetail createKoiDetail(BookingKoiDetail bookingKoiDetail);

    void deletebyBookingKoiDetail(Long theId);

    List<BookingKoiDetail> bookingKoiDetails(Long bookingID);

    BookingKoiDetail updateBookingKoiDetail(BookingKoiDetail bookingKoiDetail);
}
