package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.BookingType;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotDeleteException;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IBookingTourDetailRepository;
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

    @Override
    public Bookings createTourBooking(Tours tours, Bookings booking, int participants) {
        booking.setPaymentStatus(PaymentStatus.pending);// Set default status to pending
        booking.setBookingType(BookingType.BookingForTour);
        BookingTourDetail bookingTourDetail = new BookingTourDetail(booking, tours, participants);
        bookingTourDetail.setTotalAmount(tours.getUnitPrice() * participants);
        float totalBookingAmount = 0;
        List<BookingTourDetail> tourDetailOfBookingID = iBookingTourDetailRepository.showDetailOfBookingID(booking.getId());
        for(BookingTourDetail b : tourDetailOfBookingID){
            totalBookingAmount += b.getTotalAmount();
        }
        booking.setTotalAmount(totalBookingAmount);
        booking.setTotalAmountWithVAT(booking.getTotalAmount() + booking.getVatAmount() - booking.getDiscountAmount());
        iBookingTourDetailRepository.save(bookingTourDetail);
        return bookingRepository.save(booking);
    }

    @Override
    public List<Bookings> getTourBooking(Long accountID) {
        return bookingRepository.listTourBooking(accountID);
    }

    @Override
    public Bookings updateBooking(Long id, Bookings bookingUpdateDetail) {
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
}
