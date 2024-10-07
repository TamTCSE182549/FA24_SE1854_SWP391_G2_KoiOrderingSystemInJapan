package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

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
    public Bookings updateBooking(Long id, Bookings bookingDetails) {
        Optional<Bookings> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            Bookings bookingToUpdate = existingBooking.get();

            // Update fields except paymentStatus if it's pending or cancelled
            if (bookingToUpdate.getPaymentStatus() != PaymentStatus.pending &&
                    bookingToUpdate.getPaymentStatus() != PaymentStatus.cancelled) {
                bookingToUpdate.setPaymentStatus(bookingDetails.getPaymentStatus());
            }
            bookingToUpdate.setPaymentMethod(bookingToUpdate.getPaymentMethod());
            bookingToUpdate.setDiscountAmount(bookingDetails.getDiscountAmount());
            bookingToUpdate.setVat(bookingDetails.getVat());
            bookingToUpdate.setVatAmount(bookingToUpdate.getVat() * bookingToUpdate.getTotalAmount());
            return bookingRepository.save(bookingToUpdate);
        }
        return null; // Or throw an exception
    }

    @Override
    public boolean deleteBooking(Long id) {
        Optional<Bookings> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            Bookings bookingToUpdate = existingBooking.get();
            bookingToUpdate.setPaymentStatus(PaymentStatus.cancelled); // Update status to cancelled
            bookingRepository.save(bookingToUpdate); // Save the updated booking
            return true; // Return true indicating successful cancellation
        }
        return false; // Return false if booking was not found
    }
}
