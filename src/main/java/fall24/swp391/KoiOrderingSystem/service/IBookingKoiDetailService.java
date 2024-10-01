package fall24.swp391.KoiOrderingSystem.service;


import fall24.swp391.KoiOrderingSystem.pojo.BookingKoiDetail;

import java.util.List;

public interface IBookingKoiDetailService {

    List<BookingKoiDetail> findAll();

    BookingKoiDetail findById(Long Id);

    BookingKoiDetail save(BookingKoiDetail bookingKoiDetail);

    void deletebyId(Long theId);
}
