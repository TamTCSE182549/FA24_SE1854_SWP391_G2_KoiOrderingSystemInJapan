package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.Role;
import fall24.swp391.KoiOrderingSystem.exception.*;
import fall24.swp391.KoiOrderingSystem.model.request.BookingKoiDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.request.UpdateBookingKoiDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.BookingKoiDetailResponse;
import fall24.swp391.KoiOrderingSystem.pojo.*;
import fall24.swp391.KoiOrderingSystem.repo.IBookingKoiDetailRepository;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoisRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BookingKoiDetailService implements IBookingKoiDetailService {

    @Autowired
    private IBookingKoiDetailRepository bookingKoiDetailRepository;

    @Autowired
    private IKoisRepository iKoisRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private IBookingRepository iBookingRepository;

    @Override
    public BookingKoiDetailResponse createKoiDetail(BookingKoiDetailRequest bookingKoiDetailRequest, Long bookingId) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole() != Role.SALES_STAFF) {
                throw new NotCreateException("Your role cannot access");
            }
            Bookings bookings = iBookingRepository.findById(bookingId)
                    .orElseThrow(() -> new NotFoundEntity("Booking Not Found"));
            Kois kois = iKoisRepository.findById(bookingKoiDetailRequest.getKoiId())
                    .orElseThrow(() -> new NotFoundEntity("Not Found Koi Id"));
            BookingKoiDetail bookingKoiDetail = modelMapper.map(bookingKoiDetailRequest, BookingKoiDetail.class);
            bookingKoiDetail.setTotalAmount(bookingKoiDetailRequest.getUnitPrice() * bookingKoiDetailRequest.getQuantity());
            bookingKoiDetailRepository.save(bookingKoiDetail);

            float totalBookingAmount = 0;
            List<BookingKoiDetail> koiDetailOfBookingID = bookingKoiDetailRepository.showDetailOfBookingID(bookings.getId());
            for (BookingKoiDetail b : koiDetailOfBookingID) {
                totalBookingAmount += b.getTotalAmount();
            }
            bookings.setTotalAmount(totalBookingAmount);
            bookings.setTotalAmountWithVAT(bookings.getTotalAmount() + bookings.getVatAmount() - bookings.getDiscountAmount());
            iBookingRepository.save(bookings);

            BookingKoiDetailResponse bookingKoiDetailResponse = modelMapper.map(bookingKoiDetail, BookingKoiDetailResponse.class);
            bookingKoiDetailResponse.setBookingKoiDetailId(bookingKoiDetail.getId());
            return bookingKoiDetailResponse;
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }

    }

    @Override
    public void deletebyBookingKoiDetail(Long bookingKoiDetailId) {
        try {
            Account account = authenticationService.getCurrentAccount();
            Optional<BookingKoiDetail> bookingKoiDetail = bookingKoiDetailRepository.findById(bookingKoiDetailId);
            if (bookingKoiDetail.isPresent()) {
                Bookings bookings = bookingKoiDetail.get().getBooking();
                bookingKoiDetailRepository.deleteById(bookingKoiDetailId);
                float totalBookingAmount = 0;
                List<BookingKoiDetail> bookingKoiDetails = bookingKoiDetailRepository.showDetailOfBookingID(bookings.getId());
                for (BookingKoiDetail b : bookingKoiDetails) {
                    totalBookingAmount += b.getTotalAmount();
                }
                bookings.setTotalAmount(totalBookingAmount);
                bookings.setVatAmount(bookings.getVat() * (bookings.getTotalAmount() - bookings.getDiscountAmount()));
                bookings.setTotalAmountWithVAT(bookings.getTotalAmount() + bookings.getVatAmount() - bookings.getDiscountAmount());
                bookings.setUpdatedBy(account);
                iBookingRepository.save(bookings);
            } else {
                throw new NotDeleteException("Delete Booking Koi deatil ID" + bookingKoiDetailId + "failed");
            }
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public List<BookingKoiDetail> bookingKoiDetails(Long bookingID) {
        return bookingKoiDetailRepository.showDetailOfBookingID(bookingID);
    }

    @Override
    public List<BookingKoiDetail> updateBookingKoiDetail(Long bookingId, List<UpdateBookingKoiDetailRequest> updateBookingKoiDetailRequest) {
        try {
            Bookings booking = iBookingRepository.findById(bookingId)
                    .orElseThrow(() -> new NotUpdateException("Booking not found"));
            List<BookingKoiDetail> updatedDetails = new ArrayList<>();
            for (UpdateBookingKoiDetailRequest detailRequest : updateBookingKoiDetailRequest) {
                BookingKoiDetail koiDetail = bookingKoiDetailRepository.findById(detailRequest.getId())
                        .orElseThrow(() -> new NotFoundEntity("Not Found BookingKoiDetail"));
                    koiDetail.setQuantity(detailRequest.getQuantity());
                    koiDetail.setUnitPrice(detailRequest.getUnitPrice());
                    koiDetail.setTotalAmount(koiDetail.getUnitPrice() * koiDetail.getQuantity());
                    updatedDetails.add(bookingKoiDetailRepository.save(koiDetail));
                    koiDetail.setBooking(booking);
                }
            float totalAmount =0;
            List<BookingKoiDetail> bookingKoiDetails = bookingKoiDetailRepository.showDetailOfBookingID(bookingId);
            for (BookingKoiDetail b : bookingKoiDetails) {
                totalAmount += b.getTotalAmount();
            }
            booking.setTotalAmount(totalAmount);
            booking.setVatAmount(booking.getVat()*(totalAmount-booking.getDiscountAmount()));
            booking.setTotalAmountWithVAT(totalAmount+booking.getVatAmount()-booking.getDiscountAmount());
            bookingRepository.save(booking);
            return updatedDetails;
        } catch (Exception exception) {
            throw new GenericException(exception.getMessage());
        }
    }
}
