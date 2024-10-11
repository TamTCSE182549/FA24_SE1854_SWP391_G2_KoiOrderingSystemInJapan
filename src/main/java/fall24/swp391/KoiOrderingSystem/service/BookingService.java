package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.BookingType;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.enums.Role;
import fall24.swp391.KoiOrderingSystem.exception.*;
import fall24.swp391.KoiOrderingSystem.model.request.BookingTourRequest;
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
public class BookingService implements IBookingService{

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IBookingTourDetailRepository iBookingTourDetailRepository;

    @Autowired
    private ITourRepository iTourRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookingTourResponse createTourBooking(BookingTourRequest bookingTourRequest) throws Exception{
        try {
            Account account = authenticationService.getCurrentAccount();
            if(account.getRole() == Role.CUSTOMER){
                Tours tours = iTourRepository.findById(bookingTourRequest.getTourID())
                        .orElseThrow(() -> new NotFoundEntity("Tour not found"));
                //nếu lỗi là say bye
                Bookings booking = new Bookings();
                booking.setPaymentMethod(bookingTourRequest.getPaymentMethod());
                booking.setCreatedBy(account);
                booking.setPaymentStatus(PaymentStatus.pending);// Set default status to pending
                booking.setBookingType(BookingType.BookingForTour);
                bookingRepository.save(booking);
                //Save booking
                BookingTourDetail bookingTourDetail = new BookingTourDetail(booking, tours, bookingTourRequest.getParticipants());
                bookingTourDetail.setTotalAmount(tours.getUnitPrice() * bookingTourDetail.getParticipant());
                iBookingTourDetailRepository.save(bookingTourDetail);
                float totalBookingAmount = 0;
                List<BookingTourDetail> tourDetailOfBookingID = iBookingTourDetailRepository.showDetailOfBookingID(booking.getId());
                for(BookingTourDetail b : tourDetailOfBookingID){
                    totalBookingAmount += b.getTotalAmount();
                }
                booking.setTotalAmount(totalBookingAmount);
                booking.setTotalAmountWithVAT(booking.getTotalAmount() + booking.getVatAmount() - booking.getDiscountAmount());
                bookingRepository.save(booking);
                BookingTourResponse bookingTourResponse = modelMapper.map(booking, BookingTourResponse.class);
                bookingTourResponse.setCreatedBy(account.getFirstName()+ " " + account.getLastName());
                return bookingTourResponse;
            } else {
                throw new NotCreateException("Create Booking Only Role Manager");
            }
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public List<Bookings> getTourBooking(Long accountID) {
        return bookingRepository.listTourBooking(accountID);
    }

    @Override
    public List<BookingTourResponse> getTourBookingResponse(Long idAccount) {
        List<Bookings> bookingsList = bookingRepository.listTourBooking(idAccount);
        return bookingsList.stream().map(bookings -> {
            BookingTourResponse bookingTourResponse = modelMapper.map(bookings, BookingTourResponse.class);
            bookingTourResponse.setCreatedBy(bookings.getCreatedBy().getFirstName()+ " " + bookings.getCreatedBy().getLastName());
            bookingTourResponse.setUpdatedBy(bookings.getUpdatedBy().getFirstName()+ " " + bookings.getUpdatedBy().getLastName());
            return bookingTourResponse;
        }).toList();
    }

    @Override
    public Bookings updateTourBooking(Long id, Bookings bookingUpdateDetail) {
        try {
            Optional<Bookings> existingBooking = bookingRepository.findById(id);
            if (existingBooking.isPresent()) {
                Bookings bookingToUpdate = existingBooking.get();
                // Update fields except paymentStatus if it's pending or cancelled
                if (bookingToUpdate.getPaymentStatus() != PaymentStatus.pending &&
                        bookingToUpdate.getPaymentStatus() != PaymentStatus.cancelled) {
                    bookingToUpdate.setPaymentStatus(bookingUpdateDetail.getPaymentStatus());
                }
                bookingToUpdate.setPaymentMethod(bookingToUpdate.getPaymentMethod());
                bookingToUpdate.setDiscountAmount(bookingUpdateDetail.getDiscountAmount());
                bookingToUpdate.setVat(bookingUpdateDetail.getVat());
                bookingToUpdate.setVatAmount(bookingToUpdate.getVat() * bookingToUpdate.getTotalAmount());
                bookingToUpdate.setUpdatedBy(authenticationService.getCurrentAccount());
                float totalBookingAmount = 0;
                List<BookingTourDetail> tourDetailOfBookingID = iBookingTourDetailRepository.showDetailOfBookingID(id);
                for(BookingTourDetail b : tourDetailOfBookingID){
                    totalBookingAmount += b.getTotalAmount();
                }
                bookingToUpdate.setTotalAmount(totalBookingAmount);
                bookingToUpdate.setTotalAmountWithVAT(bookingToUpdate.getTotalAmount() + bookingToUpdate.getVatAmount() - bookingToUpdate.getDiscountAmount());
                return bookingRepository.save(bookingToUpdate);
            } else {
                throw new NotUpdateException("Update booking id " + id + " failed");
            }
        } catch (NotUpdateException e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public BookingTourResponse updateTourBookingResponse(Bookings bookingDetails) {
        Bookings booking = bookingRepository.findById(bookingDetails.getId())
                .orElseThrow(() -> new NotFoundEntity("Booking not exists"));
        Account account = authenticationService.getCurrentAccount();
        booking.setVat(bookingDetails.getVat());
        booking.setVatAmount(booking.getVat() * booking.getTotalAmount());
        booking.setDiscountAmount(bookingDetails.getDiscountAmount());
        booking.setTotalAmountWithVAT(booking.getTotalAmount() + booking.getVatAmount() - booking.getDiscountAmount());
        booking.setPaymentMethod(bookingDetails.getPaymentMethod());
        booking.setUpdatedBy(account);
        bookingRepository.save(booking);
        BookingTourResponse bookingTourResponse = modelMapper.map(booking, BookingTourResponse.class);
        bookingTourResponse.setCreatedBy(booking.getCreatedBy().getFirstName() +  " " + booking.getCreatedBy().getLastName());
        bookingTourResponse.setUpdatedBy(booking.getUpdatedBy().getFirstName() +  " " + booking.getUpdatedBy().getLastName());
        return modelMapper.map(booking, BookingTourResponse.class);
    }

    //delete means update payment_status to cancelled
    @Override
    public Bookings deleteBooking(Long id) {
        try {
            Optional<Bookings> existingBooking = bookingRepository.findById(id);
            if (existingBooking.isPresent()) {
                Bookings bookingToUpdate = existingBooking.get();
                bookingToUpdate.setPaymentStatus(PaymentStatus.cancelled); // Update status to cancelled
                return bookingRepository.save(bookingToUpdate); // Save the updated booking
            } else {
                throw new NotDeleteException("Can not DELETE booking " + id);
            }
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public BookingTourResponse deleteBookingResponse(Long bookingID) {
        Bookings booking = bookingRepository.findById(bookingID)
                .orElseThrow(() -> new NotFoundEntity("Booking not exist"));
        Account account = authenticationService.getCurrentAccount();
        booking.setPaymentStatus(PaymentStatus.cancelled);
        booking.setUpdatedBy(account);
        bookingRepository.save(booking);
        BookingTourResponse bookingTourResponse = modelMapper.map(booking, BookingTourResponse.class);
        bookingTourResponse.setCreatedBy(booking.getCreatedBy().getFirstName() +  " " + booking.getCreatedBy().getLastName());
        bookingTourResponse.setUpdatedBy(booking.getUpdatedBy().getFirstName() +  " " + booking.getUpdatedBy().getLastName());
        return modelMapper.map(booking, BookingTourResponse.class);
    }
}
