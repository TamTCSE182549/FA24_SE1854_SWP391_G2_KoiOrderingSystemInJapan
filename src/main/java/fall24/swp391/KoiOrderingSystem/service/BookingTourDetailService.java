package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import fall24.swp391.KoiOrderingSystem.repo.IBookingTourDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingTourDetailService implements IBookingTourDetailService {

    @Autowired
    private IBookingTourDetailRepository iBookingTourDetailRepository;

    @Override
    public BookingTourDetail bookingTourDetails(Long bookingID) {
        return iBookingTourDetailRepository.showDetail(bookingID);
    }
}
