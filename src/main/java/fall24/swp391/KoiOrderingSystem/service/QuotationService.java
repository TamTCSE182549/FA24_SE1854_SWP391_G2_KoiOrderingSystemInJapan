package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.ApproveStatus;
import fall24.swp391.KoiOrderingSystem.enums.PaymentStatus;
import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.model.request.QuotationRequest;
import fall24.swp391.KoiOrderingSystem.model.response.QuotationResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Quotations;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IBookingTourDetailRepository;
import fall24.swp391.KoiOrderingSystem.repo.IQuotationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<QuotationResponse> getQuotationsByBookID(Long bookId) {
        List<QuotationResponse> quotationResponses = new ArrayList<>();
        List<Quotations> quotationsList = quotationRepository.findByBookingId(bookId);
        for (Quotations quotation : quotationsList) {
            QuotationResponse quotationResponse = modelMapper.map(quotation, QuotationResponse.class);
            quotationResponse.setBookingId(bookId);
            if(quotation.getCreatedBy()!=null)
            quotationResponse.setStaffName(quotation.getCreatedBy().getFirstName()+" "+quotation.getCreatedBy().getLastName());
            if(quotation.getApproveBy()!=null)
            quotationResponse.setManagerName(quotation.getApproveBy().getFirstName()+" "+quotation.getApproveBy().getLastName());
            quotationResponses.add(quotationResponse);
        }
        return quotationResponses;
    } //Lấy Quotations từ Booking, để lấy amount từ BookingTourDetail

    @Override
    public QuotationResponse createQuotations(QuotationRequest quotationRequest) {
        Bookings bookings = bookingRepository.findBookingsById(quotationRequest.getBookingId());
        if (bookings== null) {
            throw new NotFoundEntity("Booking not found");
        }
        Quotations quotation = new Quotations();
        quotation.setAmount(quotationRequest.getAmount());
        quotation.setBooking(bookings);
        quotation.setIsApprove(ApproveStatus.PROCESS);
        Account account = authenticationService.getCurrentAccount();
        quotation.setCreatedBy(account);
        //create initialization status
        quotationRepository.save(quotation);
        QuotationResponse quotationResponse = modelMapper.map(quotation, QuotationResponse.class);
        quotationResponse.setBookingId(quotation.getBooking().getId());
        quotationResponse.setStaffName(quotation.getCreatedBy().getFirstName()+" "+quotation.getCreatedBy().getLastName());
        if(quotation.getApproveBy()!=null)
        quotationResponse.setManagerName(quotation.getApproveBy().getFirstName()+" "+quotation.getApproveBy().getLastName());
        return quotationResponse;
    }

    @Override
    public boolean deleteQuotations(Long QuotationId) {
        Quotations quotations = quotationRepository.findQuotationsById(QuotationId);
        if(quotations==null) {
           throw new NotFoundEntity("Quotation not found");
        }
        quotationRepository.deleteById(QuotationId);
        return true;
    }

    @Override
    public QuotationResponse adminUpdateStatusQuotations(Long quotationId, ApproveStatus approveStatus) {
        Quotations quotations;
        QuotationResponse quotationResponse;
        try {
            Account account = authenticationService.getCurrentAccount();
            quotations = quotationRepository.findQuotationsById(quotationId);
            if (quotations == null) {
                throw new NotFoundEntity("Quotation not found");
            }
//            quotations.setIsApprove(approveStatus);
            quotations.setApproveBy(account);
            quotations.setApproveTime(LocalDateTime.now());
            quotationRepository.save(quotations);
            quotationResponse = modelMapper.map(quotations, QuotationResponse.class);
            quotationResponse.setBookingId(quotations.getBooking().getId());
            quotationResponse.setStaffName(quotations.getCreatedBy().getFirstName() + " " + quotations.getCreatedBy().getLastName());
            if(quotations.getApproveBy()!=null)
            quotationResponse.setManagerName(quotations.getApproveBy().getFirstName() + " " + quotations.getApproveBy().getLastName());

        } catch (NotUpdateException e) {
            throw new GenericException(e.getMessage());
        }
        return quotationResponse;
    }

    @Override
    public QuotationResponse updateAmountQuotations(Long quotationId,float amount) {
        Quotations quotations;
        QuotationResponse quotationResponse;
        try{
            quotations = quotationRepository.findQuotationsById(quotationId);
            if(quotations==null){
                throw new NotFoundEntity("Quotation not found");
            }
            if(amount > 0){
                quotations.setAmount(amount);
                quotationRepository.save(quotations);
                quotationResponse = modelMapper.map(quotations, QuotationResponse.class);
                quotationResponse.setBookingId(quotations.getBooking().getId());
                quotationResponse.setStaffName(quotations.getCreatedBy().getFirstName() + " " + quotations.getCreatedBy().getLastName());
                if(quotations.getApproveBy()!=null)
                quotationResponse.setManagerName(quotations.getApproveBy().getFirstName() + " " + quotations.getApproveBy().getLastName());

            }
            else throw new GenericException("Price > 0");
        } catch (NotUpdateException e) {
            throw new GenericException(e.getMessage());
        }
        return quotationResponse;
    }

    @Override
    public QuotationResponse updateStatusQuotations(Long quotationId,ApproveStatus status) {
        Quotations quotations;
        QuotationResponse quotationResponse;
        try {
            quotations = quotationRepository.findQuotationsById(quotationId);
            if(quotations==null){
                throw new NotFoundEntity("Quotation not found");
            }
            quotations.setIsApprove(status);
            quotationRepository.save(quotations);
            quotationResponse = modelMapper.map(quotations, QuotationResponse.class);
            quotationResponse.setBookingId(quotations.getBooking().getId());
            quotationResponse.setStaffName(quotations.getCreatedBy().getFirstName() + " " + quotations.getCreatedBy().getLastName());
            if(quotations.getApproveBy()!=null)
            quotationResponse.setManagerName(quotations.getApproveBy().getFirstName() + " " + quotations.getApproveBy().getLastName());

        } catch (NotUpdateException e) {
            throw new GenericException(e.getMessage());
        }
        return quotationResponse;
    }

}
