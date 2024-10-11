package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotDeleteException;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.model.request.BookingTourDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.BookingTourDetailResponse;
import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IBookingTourDetailRepository;
import fall24.swp391.KoiOrderingSystem.repo.ITourRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IBookingRepository iBookingRepository;

    @Autowired
    private IBookingService iBookingService;

    @Override
    public List<BookingTourDetail> bookingTourDetails(Long bookingID) {
        return iBookingTourDetailRepository.showDetailOfBookingID(bookingID);
    }

    @Override
    public List<BookingTourDetailResponse> bookingTourDetailRes(Long bookingID) {
        List<BookingTourDetail> bookingTourDetailList = iBookingTourDetailRepository.showDetailOfBookingID(bookingID);
        return bookingTourDetailList.stream().map(
                bookingTourDetail -> {
                    BookingTourDetailResponse bookingTourDetailResponse = modelMapper.map(bookingTourDetail, BookingTourDetailResponse.class);
                    bookingTourDetailResponse.setTourName(bookingTourDetail.getTourId().getTourName());
                    return bookingTourDetailResponse;
                }
        ).toList();
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
    public BookingTourDetailResponse createBookingTourDetailRes(BookingTourDetailRequest bookingTourDetailRequest) {
        Bookings bookings = iBookingRepository.findById(bookingTourDetailRequest.getBookingID())
                .orElseThrow(() -> new NotFoundEntity("Booking ID not FOUND"));
        Tours tours = iTourRepository.findById(bookingTourDetailRequest.getTourID())
                .orElseThrow(() -> new NotFoundEntity("Tour ID not FOUND"));
        BookingTourDetail bookingTourDetail = new BookingTourDetail(bookings, tours, bookingTourDetailRequest.getParticipant());
        bookingTourDetail.setTotalAmount(tours.getUnitPrice() * bookingTourDetail.getParticipant());
        iBookingTourDetailRepository.save(bookingTourDetail);
        iBookingService.updateTourBookingResponse(bookings);
        BookingTourDetailResponse bookingTourDetailResponse = modelMapper.map(bookingTourDetail, BookingTourDetailResponse.class);
        bookingTourDetailResponse.setBookingTourDetailID(bookingTourDetail.getId());
        return bookingTourDetailResponse;
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
