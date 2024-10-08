package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import fall24.swp391.KoiOrderingSystem.repo.IBookingTourDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingTourDetailService implements IBookingTourDetailService {

    @Autowired
    private IBookingTourDetailRepository iBookingTourDetailRepository;

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
    public boolean deleteBookingTourDetail(Long id){
        Optional<BookingTourDetail> bookingTourDetail = iBookingTourDetailRepository.findById(id);
        if (bookingTourDetail.isPresent()){
            iBookingTourDetailRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public BookingTourDetail updateBookingTourDetail(BookingTourDetail bookingTourDetail){
        //Lấy unit price từ Tour để tính total amount bên bảng BookingTourDetail
        try {
            Optional<BookingTourDetail> bTourDetail = iBookingTourDetailRepository.findById(bookingTourDetail.getId());
            if (bTourDetail.isPresent()){
                BookingTourDetail update = bTourDetail.get();
                update.setParticipant(bookingTourDetail.getParticipant());
                //còn sửa chỗ này
                update.setTotalAmount(bookingTourDetail.getParticipant());
                return iBookingTourDetailRepository.save(bookingTourDetail);
            } else {
                throw new NotUpdateException("Update booking tour detail Failed");
            }
        } catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }
}
