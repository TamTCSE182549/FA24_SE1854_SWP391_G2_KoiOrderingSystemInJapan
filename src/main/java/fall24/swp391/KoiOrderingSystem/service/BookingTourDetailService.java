package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.enums.Role;
import fall24.swp391.KoiOrderingSystem.exception.*;
import fall24.swp391.KoiOrderingSystem.model.request.BookingTourDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.BookingTourDetailResponse;
import fall24.swp391.KoiOrderingSystem.model.response.BookingTourResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
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

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public List<BookingTourDetailResponse> bookingTourDetails(Long bookingID) {
        try {
            Account account = authenticationService.getCurrentAccount();
//            if (account.getRole()!= Role.CUSTOMER){
//                throw new NotReadException("You cannot access here");
//            }
            Bookings bookings = iBookingRepository.findById(bookingID)
                    .orElseThrow(() -> new NotFoundEntity("Not FOUND Booking to show Detail of Booking"));
            List<BookingTourDetail> bookingTourDetailList = iBookingTourDetailRepository.showDetailOfBookingID(bookings.getId());
            return bookingTourDetailList.stream().map(bookingTourDetail -> {
                BookingTourDetailResponse bookingTourDetailResponse = modelMapper.map(bookingTourDetail, BookingTourDetailResponse.class);
                bookingTourDetailResponse.setBookingTourDetailID(bookingTourDetail.getId());
                bookingTourDetailResponse.setBookingID(bookings.getId());
                bookingTourDetailResponse.setTourName(bookingTourDetail.getTourId().getTourName());
                return bookingTourDetailResponse;
            }).toList();
        } catch (Exception e){
            throw new GenericException(e.getMessage());
        }
    }

//    @Override
//    public List<BookingTourDetailResponse> bookingTourDetailRes(Long bookingID) {
//        List<BookingTourDetail> bookingTourDetailList = iBookingTourDetailRepository.showDetailOfBookingID(bookingID);
//        return bookingTourDetailList.stream().map(
//                bookingTourDetail -> {
//                    BookingTourDetailResponse bookingTourDetailResponse = modelMapper.map(bookingTourDetail, BookingTourDetailResponse.class);
//                    bookingTourDetailResponse.setTourName(bookingTourDetail.getTourId().getTourName());
//                    return bookingTourDetailResponse;
//                }
//        ).toList();
//    }

//    @Override
//    public List<BookingTourDetail> getAll() {
//        return iBookingTourDetailRepository.findAll();
//    }

    //Khi khởi tạo thì phải setTotal amount bằng unit price nhân với người tham gia
//    @Override
//    public BookingTourDetail createBookingTourDetail(BookingTourDetail bookingTourDetail){
//        return iBookingTourDetailRepository.save(bookingTourDetail);
//    }

    @Override
    public BookingTourDetailResponse createBookingTourDetailRes(BookingTourDetailRequest bookingTourDetailRequest) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole()!=Role.CUSTOMER){
                throw new NotCreateException("Your role cannot access");
            }
            Bookings bookings = iBookingRepository.findById(bookingTourDetailRequest.getBookingID())
                    .orElseThrow(() -> new NotFoundEntity("Booking ID not FOUND"));
            if(bookings.getPaymentStatus()== PaymentStatus.complete){
                throw new NotCreateException("This booking had complete, so You cannot add tour more!");
            }
            Tours tours = iTourRepository.findById(bookingTourDetailRequest.getTourID())
                    .orElseThrow(() -> new NotFoundEntity("Tour ID not FOUND"));
            BookingTourDetail bookingTourDetail = new BookingTourDetail(bookings, tours, bookingTourDetailRequest.getParticipant());
            bookingTourDetail.setTotalAmount(tours.getUnitPrice() * bookingTourDetail.getParticipant());
            iBookingTourDetailRepository.save(bookingTourDetail);

            float totalBookingAmount = 0;
            List<BookingTourDetail> tourDetailOfBookingID = iBookingTourDetailRepository.showDetailOfBookingID(bookings.getId());
            for(BookingTourDetail b : tourDetailOfBookingID){
                totalBookingAmount += b.getTotalAmount();
            }
            bookings.setTotalAmount(totalBookingAmount);
            bookings.setTotalAmountWithVAT(bookings.getTotalAmount() + bookings.getVatAmount() - bookings.getDiscountAmount());
            iBookingRepository.save(bookings);

            BookingTourDetailResponse bookingTourDetailResponse = modelMapper.map(bookingTourDetail, BookingTourDetailResponse.class);
            bookingTourDetailResponse.setBookingTourDetailID(bookingTourDetail.getId());
            return bookingTourDetailResponse;
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public void deleteBookingTourDetail(Long bookingTourDetailID){
        try {
            Optional<BookingTourDetail> bookingTourDetail = iBookingTourDetailRepository.findById(bookingTourDetailID);
            Account account = authenticationService.getCurrentAccount();
            if (bookingTourDetail.isPresent()){
                Bookings bookings = bookingTourDetail.get().getBooking();
                iBookingTourDetailRepository.deleteById(bookingTourDetailID);
                float totalBookingAmount = 0;
                List<BookingTourDetail> tourDetailOfBookingID = iBookingTourDetailRepository.showDetailOfBookingID(bookings.getId());
                for(BookingTourDetail b : tourDetailOfBookingID){
                    totalBookingAmount += b.getTotalAmount();
                }
                bookings.setTotalAmount(totalBookingAmount);
                bookings.setVatAmount(bookings.getVat() * bookings.getTotalAmount());
                bookings.setTotalAmountWithVAT(bookings.getTotalAmount() + bookings.getVatAmount() - bookings.getDiscountAmount());
                bookings.setUpdatedBy(account);
                iBookingRepository.save(bookings);
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
                Tours tour = iTourRepository.findById(bookingTourDetail.getTourId().getId()).
                        orElseThrow(() -> new NotFoundEntity("Tour Not Found"));
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
