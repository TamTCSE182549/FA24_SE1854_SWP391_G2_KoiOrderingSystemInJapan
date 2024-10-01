package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
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
    public Optional<Bookings> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public List<Bookings> getAllBookings() {
        return bookingRepository.findAll();
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

            // Set other fields as needed (e.g., totalAmount, bookingType, etc.)
            // bookingToUpdate.setTotalAmount(bookingDetails.getTotalAmount());
            // Add other updates as necessary

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
