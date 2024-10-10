package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Quotations;

import java.util.List;

public interface IQuotationService {

    List<Quotations> getQuotationsByBookID(Long bookId);

    Quotations createQuotations(Quotations quotations, float amount);

    void updateQuotations(Long bookId);

    boolean deleteQuotations(Long bookId);
}
