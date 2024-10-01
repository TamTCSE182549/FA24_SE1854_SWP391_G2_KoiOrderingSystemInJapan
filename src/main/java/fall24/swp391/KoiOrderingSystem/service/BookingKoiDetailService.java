package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.BookingKoiDetail;
import fall24.swp391.KoiOrderingSystem.repo.IBookingKoiDetailRepository;

import java.util.List;
import java.util.Optional;

public class BookingKoiDetailService implements IBookingKoiDetailService{

    private IBookingKoiDetailRepository bookingKoiDetailRepository;

    public BookingKoiDetailService(IBookingKoiDetailRepository thebookingKoiDetailRepository) {
        bookingKoiDetailRepository = thebookingKoiDetailRepository;
    }

    @Override
    public List<BookingKoiDetail> findAll() {
        return bookingKoiDetailRepository.findAll();
    }

    @Override
    public BookingKoiDetail findById(Long Id) {
        Optional<BookingKoiDetail> result = bookingKoiDetailRepository.findById(Id);
        BookingKoiDetail theBooking = null;
        if (result.isPresent()) {
            theBooking = result.get();
        } else {
            throw new RuntimeException("Did not find employee id - " + Id);
        }
        return theBooking;
    }

    @Override
    public BookingKoiDetail save(BookingKoiDetail bookingKoiDetail) {
        return bookingKoiDetailRepository.save(bookingKoiDetail);
    }

    @Override
    public void deletebyId(Long theId) {
        bookingKoiDetailRepository.deleteById(theId);
    }
}
