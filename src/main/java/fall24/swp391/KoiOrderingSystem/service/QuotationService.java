package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.ApproveStatus;
import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Quotations;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IBookingTourDetailRepository;
import fall24.swp391.KoiOrderingSystem.repo.IQuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuotationService implements IQuotationService{

    @Autowired
    private IQuotationRepository quotationRepository;

    @Autowired
    private IBookingTourDetailRepository bookingTourDetailRepository;

    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public List<Quotations> getQuotationsByBookID(Long quotationId) {
        List<Quotations> quotationsList = quotationRepository.findQuotationById(quotationId);
        return quotationsList;
    }

    @Override
    public Quotations createQuotations(Quotations quotations, float amount) {
        Long bookId = quotations.getBooking().getId();
        Bookings booking = bookingRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Booking Not Found For Quotations"));
        amount = bookingTourDetailRepository.findTotalAmount(bookId);

        quotations.setIsApprove(ApproveStatus.PROCESS);
        quotations.setAmount(amount);
        quotations.setBooking(booking);
        return quotationRepository.save(quotations);
    }

    @Override
    public Boolean deleteQuotations(Long quotationId) {
        try{
            Optional<Quotations> quotations = quotationRepository.findById(quotationId);
            if(quotations.isPresent()){
                Quotations delQuotation = quotations.get();
                delQuotation.setIsApprove(ApproveStatus.FINISH);
                quotationRepository.save(delQuotation);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public Quotations updateQuotations(Long quotationId) {

        Optional<Quotations> optionalQuotation = quotationRepository.findById(quotationId);

        if(optionalQuotation.isPresent()){
            Quotations quotations = optionalQuotation.get();
            float totalAmount = bookingTourDetailRepository.findTotalAmount(quotationId);
            quotations.setAmount(totalAmount);
            return quotationRepository.save(quotations);
        }
        return null;

    }

}
