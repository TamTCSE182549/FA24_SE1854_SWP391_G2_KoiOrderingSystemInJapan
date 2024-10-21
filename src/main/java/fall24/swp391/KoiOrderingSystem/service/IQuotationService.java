package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.ApproveStatus;
import fall24.swp391.KoiOrderingSystem.model.request.QuotationRequest;
import fall24.swp391.KoiOrderingSystem.model.response.QuotationResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Quotations;

import java.util.List;

public interface IQuotationService {

    List<QuotationResponse> getQuotationsByBookID(Long bookId);

    List<QuotationResponse> getAllQuotation();

    QuotationResponse createQuotations(QuotationRequest quotations);

    QuotationResponse updateAmountQuotations(Long bookId, float amount);

    QuotationResponse updateStatusQuotations(Long quotationId, ApproveStatus approveStatus);

    boolean deleteQuotations(Long quotationId);

    QuotationResponse adminUpdateStatusQuotations(Long quotationId, ApproveStatus approveStatus);
}
