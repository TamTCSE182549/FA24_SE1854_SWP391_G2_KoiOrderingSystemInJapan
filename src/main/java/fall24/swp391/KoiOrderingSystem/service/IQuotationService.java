package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.ApproveStatus;
import fall24.swp391.KoiOrderingSystem.model.request.QuotationRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Quotations;

import java.util.List;

public interface IQuotationService {

    List<Quotations> getQuotationsByBookID(Long bookId);

    Quotations createQuotations(QuotationRequest quotations);

    Quotations updateAmountQuotations(Long bookId, float amount);

    Quotations updateStatusQuotations(Long quotationId, ApproveStatus approveStatus);

    boolean deleteQuotations(Long quotationId);

    Quotations adminUpdateStatusQuotations(Long quotationId, ApproveStatus approveStatus);
}
