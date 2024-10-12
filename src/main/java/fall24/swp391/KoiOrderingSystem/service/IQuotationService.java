package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Quotations;

import java.util.List;

public interface IQuotationService {

    List<Quotations> getQuotationsByBookID(Long quotationId);

    Quotations createQuotations(Quotations quotations, float amount);

    Quotations updateQuotations(Long quotationId);

    Boolean deleteQuotations(Long quotationId);
}
