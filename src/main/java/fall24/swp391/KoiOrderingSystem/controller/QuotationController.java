package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.enums.ApproveStatus;
import fall24.swp391.KoiOrderingSystem.model.request.QuotationRequest;
import fall24.swp391.KoiOrderingSystem.model.response.QuotationResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Quotations;
import fall24.swp391.KoiOrderingSystem.service.QuotationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotations")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "api")
public class QuotationController {

    @Autowired
    private QuotationService quotationService;

    //Create quotations
    @PostMapping("/create")
    public ResponseEntity<QuotationResponse> createQuotation(@RequestBody QuotationRequest quotationRequest) {
        try{
            QuotationResponse quotations = quotationService.createQuotations(quotationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(quotations);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //Lấy quotation ? theo quote id
    @GetMapping("/booking/{bookId}")
    public ResponseEntity<List<QuotationResponse>> getQuotations(@PathVariable Long bookId){
        List<QuotationResponse> quotations = quotationService.getQuotationsByBookID(bookId);
        if(quotations.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quotations);
    }

    @GetMapping("/quotation/all")
    public ResponseEntity<List<QuotationResponse>> showAllQuotation(){
        List<QuotationResponse> quotation = quotationService.getAllQuotation();
        if(quotation.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quotation);
    }

    @DeleteMapping("/delete/{quotationId}")
    public ResponseEntity<?> deleteQuotation(@PathVariable Long quotationId){
        try {
            quotationService.deleteQuotations(quotationId);
            return ResponseEntity.ok("Quotation has been deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin/{quotationId}")
    public ResponseEntity<QuotationResponse> updateAdminQuotation(@PathVariable Long quotationId, @RequestBody ApproveStatus approveStatus){
        try {
            QuotationResponse quotations= quotationService.adminUpdateStatusQuotations(quotationId, approveStatus);
            return ResponseEntity.ok(quotations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/updateAmount/{quotationId}")
    public ResponseEntity<QuotationResponse> updateQuotationAmount(@PathVariable Long quotationId, @RequestBody float amount){
        try{
            QuotationResponse quotations=quotationService.updateAmountQuotations(quotationId, amount);
            return ResponseEntity.ok(quotations);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/updateStatus/{quotationId}")
    public ResponseEntity<QuotationResponse> updateQuotationStatus(@PathVariable Long quotationId, @RequestBody ApproveStatus approveStatus){
        try{
            QuotationResponse quotations=quotationService.updateStatusQuotations(quotationId, approveStatus);
            return ResponseEntity.ok(quotations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    //Lấy amount từ bookingTourDetail
//    @PutMapping("/{id}/set-amount")
//    public ResponseEntity<?> updateQuotation(@PathVariable Long id){
//        try{
//            quotationService.updateQuotations(id);
//            return ResponseEntity.ok("Amount set successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }


}
