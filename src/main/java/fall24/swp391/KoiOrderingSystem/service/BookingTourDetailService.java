package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import fall24.swp391.KoiOrderingSystem.repo.IBookingTourDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingTourDetailService implements IBookingTourDetailService {

    @Autowired
    private IBookingTourDetailRepository iBookingTourDetailRepository;

    @Override
    public BookingTourDetail bookingTourDetails(Long bookingID) {
        return iBookingTourDetailRepository.showDetailOfBookingID(bookingID);
    }

    public BookingTourDetail createBookingTourDetail(BookingTourDetail bookingTourDetail){
        return iBookingTourDetailRepository.save(bookingTourDetail);
    }

    public boolean deleteBookingTourDetail(Long id){
        Optional<BookingTourDetail> bookingTourDetail = iBookingTourDetailRepository.findById(id);
        if (bookingTourDetail.isPresent()){
            iBookingTourDetailRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
