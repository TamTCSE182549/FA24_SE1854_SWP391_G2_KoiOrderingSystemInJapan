package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.ApproveStatus;
import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
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
    public List<Quotations> getQuotationsByBookID(Long bookId) {
        List<Quotations> quotationsList = quotationRepository.listQuotations(bookId);
        return quotationRepository.listQuotations(bookId);
    } //Lấy Quotations từ Booking, để lấy amount từ BookingTourDetail

    @Override
    public Quotations createQuotations(Quotations quotations, float amount) {
        quotations.setIsApprove(ApproveStatus.PROCESS); //create initialization status
        return quotationRepository.save(quotations);
    }

    @Override
    public boolean deleteQuotations(Long bookId) {
        Optional<Quotations> quotations = quotationRepository.findById(bookId);
        if(quotations.isPresent()){
            quotationRepository.deleteById(bookId);
            return true;
        }
        return false;
    }

    @Override
    public void updateQuotations(Long bookId) {
        try{
            Quotations quotations = quotationRepository.findById(bookId).orElseThrow();
            bookId = quotations.getBooking().getId();
            Float amount = bookingTourDetailRepository.findTotalAmount(bookId);

            if(amount != null){
                quotations.setAmount(amount);
                quotationRepository.save(quotations);
            }
        } catch (NotUpdateException e) {
            throw new GenericException(e.getMessage());
        }
    }

}
