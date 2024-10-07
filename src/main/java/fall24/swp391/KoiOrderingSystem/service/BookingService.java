package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.exception.NotDeleteException;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import org.eclipse.angus.mail.handlers.handler_base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService{

    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public Bookings createBooking(Bookings booking) {
        booking.setPaymentStatus(PaymentStatus.pending); // Set default status to pending
        return bookingRepository.save(booking);
    }

    @Override
    public List<Bookings> getTourBooking(Long accountID) {
        List<Bookings> bookingsList = bookingRepository.listTourBooking(accountID);
        return bookingRepository.listTourBooking(accountID);
    }

    @Override
    public void updateBooking(Long id, Bookings bookingUpdateDetail) {
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
                bookingRepository.save(bookingToUpdate);
            }
        } catch (NotUpdateException e) {
            throw new NotUpdateException("Update booking id " + id + "failed");
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
            throw new NotUpdateException("Error: " + e.getMessage());
        }
    }

    @Override
    public Bookings createPaymentTourPending(Bookings booking){
        Bookings savedBooking = createBooking(booking);
        float totalAmount =savedBooking.getBookingTourDetails().getFirst().getTotalAmount();
        System.out.println("Total amount to be paid: " + totalAmount);
        return savedBooking;
    }

    @Override
    public Bookings cancelledPaymentTour(Bookings booking) {
        Optional<Bookings> existingBooking = bookingRepository.findById(booking.getId());
        if(existingBooking.isPresent()){
            Bookings bookingToUpdate = existingBooking.get();
            bookingToUpdate.setPaymentStatus(PaymentStatus.cancelled);
            bookingRepository.save(bookingToUpdate);
            return bookingToUpdate;
        }
        return null;
    }


}
