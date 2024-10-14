package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.ApproveStatus;
import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.model.request.QuotationRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.Quotations;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IBookingTourDetailRepository;
import fall24.swp391.KoiOrderingSystem.repo.IQuotationRepository;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public List<Quotations> getQuotationsByBookID(Long bookId) {
        List<Quotations> quotationsList = quotationRepository.listQuotations(bookId);
        return quotationRepository.listQuotations(bookId);
    } //Lấy Quotations từ Booking, để lấy amount từ BookingTourDetail

    @Override
    public Quotations createQuotations(QuotationRequest quotationRequest) {
        Quotations quotation = new Quotations();
        quotation = modelMapper.map(quotationRequest, Quotations.class);
        quotation.setIsApprove(ApproveStatus.WAITING);
        Account account = authenticationService.getCurrentAccount();
        quotation.setCreatedBy(account);
        //create initialization status
        return quotationRepository.save(quotation);
    }

    @Override
    public boolean deleteQuotations(Long QuotationId) {
        Quotations quotations = quotationRepository.findQuotationsById(QuotationId);
        if(quotations!=null) {
            quotationRepository.deleteById(QuotationId);
            return true;
        }
        return false;
    }

    @Override
    public Quotations adminUpdateStatusQuotations(Long quotationId, ApproveStatus approveStatus) {
        Quotations quotations;
        try {
            Account account = authenticationService.getCurrentAccount();
            quotations = quotationRepository.findQuotationsById(quotationId);
            if (quotations == null) {
                throw new NotFoundEntity("Quotation not found");
            }
            quotations.setIsApprove(approveStatus);
            quotations.setApproveBy(account);
            quotationRepository.save(quotations);

        } catch (NotUpdateException e) {
            throw new GenericException(e.getMessage());
        }
        return quotations;
    }

    @Override
    public Quotations updateAmountQuotations(Long quotationId,float amount) {
        Quotations quotations;
        try{
            quotations = quotationRepository.findQuotationsById(quotationId);
            if(quotations==null){
                throw new NotFoundEntity("Quotation not found");
            }
            if(amount > 0){
                quotations.setAmount(amount);
                quotationRepository.save(quotations);
            }
            else throw new GenericException("Price > 0");
        } catch (NotUpdateException e) {
            throw new GenericException(e.getMessage());
        }
        return quotations;
    }

    @Override
    public Quotations updateStatusQuotations(Long quotationId,ApproveStatus status) {
        Quotations quotations;
        try {
            quotations = quotationRepository.findQuotationsById(quotationId);
            if(quotations==null){
                throw new NotFoundEntity("Quotation not found");
            }
            quotations.setIsApprove(status);
            quotationRepository.save(quotations);
        } catch (NotUpdateException e) {
            throw new GenericException(e.getMessage());
        }
        return quotations;
    }

}
