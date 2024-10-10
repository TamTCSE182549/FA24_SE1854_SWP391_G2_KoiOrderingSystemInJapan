package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotDeleteException;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import fall24.swp391.KoiOrderingSystem.repo.IBookingTourDetailRepository;
import fall24.swp391.KoiOrderingSystem.repo.ITourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingTourDetailService implements IBookingTourDetailService {

    @Autowired
    private IBookingTourDetailRepository iBookingTourDetailRepository;

    @Autowired
    private ITourRepository iTourRepository;

    @Override
    public List<BookingTourDetail> bookingTourDetails(Long bookingID) {
        return iBookingTourDetailRepository.showDetailOfBookingID(bookingID);
    }

    @Override
    public List<BookingTourDetail> getAll() {
        return iBookingTourDetailRepository.findAll();
    }

    //Khi khởi tạo thì phải setTotal amount bằng unit price nhân với người tham gia
    @Override
    public BookingTourDetail createBookingTourDetail(BookingTourDetail bookingTourDetail){
        return iBookingTourDetailRepository.save(bookingTourDetail);
    }

    @Override
    public void deleteBookingTourDetail(Long bookingTourDetailID){
        try {
            Optional<BookingTourDetail> bookingTourDetail = iBookingTourDetailRepository.findById(bookingTourDetailID);
            if (bookingTourDetail.isPresent()){
                iBookingTourDetailRepository.deleteById(bookingTourDetailID);
            } else {
                throw new NotDeleteException("Delete booking tour detail ID " + bookingTourDetailID + "failed");
            }
        } catch (Exception e){
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public BookingTourDetail updateBookingTourDetail(BookingTourDetail bookingTourDetail){
        //Lấy unit price từ Tour để tính total amount bên bảng BookingTourDetail
        try {
            Optional<BookingTourDetail> bTourDetail = iBookingTourDetailRepository.findById(bookingTourDetail.getId());
            if (bTourDetail.isPresent()){
                BookingTourDetail update = bTourDetail.get();
                update.setParticipant(bookingTourDetail.getParticipant());
                Tours tour = iTourRepository.findById(bookingTourDetail.getTourId().getId()).get();
                update.setTotalAmount(tour.getUnitPrice() * update.getParticipant());
                return iBookingTourDetailRepository.save(bookingTourDetail);
            } else {
                throw new NotUpdateException("Update booking tour detail Failed");
            }
        } catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }
}
