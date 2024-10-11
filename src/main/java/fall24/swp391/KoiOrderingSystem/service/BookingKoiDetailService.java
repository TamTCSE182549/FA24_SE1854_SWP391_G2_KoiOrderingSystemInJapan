package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotDeleteException;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.pojo.BookingKoiDetail;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import fall24.swp391.KoiOrderingSystem.repo.IBookingKoiDetailRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BookingKoiDetailService implements IBookingKoiDetailService{

    @Autowired
    private IBookingKoiDetailRepository bookingKoiDetailRepository;

    @Autowired
    private IKoisRepository iKoisRepository;
    @Override
    public List<BookingKoiDetail> findAll() {
        return bookingKoiDetailRepository.findAll();
    }

    @Override
    public BookingKoiDetail createKoiDetail(BookingKoiDetail bookingKoiDetail) {
        return bookingKoiDetailRepository.save(bookingKoiDetail);
    }

    @Override
    public void deletebyBookingKoiDetail(Long theId) {
        try{
            Optional<BookingKoiDetail> bookingKoiDetail = bookingKoiDetailRepository.findById(theId);
            if(bookingKoiDetail.isPresent()){
                bookingKoiDetailRepository.deleteById(theId);
            }else{
                throw new NotDeleteException("Delete Booking Koi deatil ID"+theId+"failed");
            }
        }catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public List<BookingKoiDetail> bookingKoiDetails(Long bookingID) {
        return bookingKoiDetailRepository.showDetailOfBookingID(bookingID);
    }

    @Override
    public BookingKoiDetail updateBookingKoiDetail(BookingKoiDetail bookingKoiDetail) {
        try{
            Optional<BookingKoiDetail> bookingKoiDetail1 =bookingKoiDetailRepository.findById(bookingKoiDetail.getId());
            if(bookingKoiDetail1.isPresent()){
                BookingKoiDetail koiDetail =bookingKoiDetail1.get();
                koiDetail.setQuantity(bookingKoiDetail.getQuantity());
                Kois kois= iKoisRepository.findById(bookingKoiDetail.getKoi().getId()).get();
                koiDetail.setTotalAmount(kois.getUnitPrice()* koiDetail.getQuantity());
                return bookingKoiDetailRepository.save(koiDetail);
            }else{
                throw new NotUpdateException("Update Booking Kois detail failed");
            }
        }catch (Exception exception) {
            throw new GenericException(exception.getMessage());
        }
    }
}
