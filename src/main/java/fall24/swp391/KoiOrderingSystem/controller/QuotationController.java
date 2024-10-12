package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.pojo.Quotations;
import fall24.swp391.KoiOrderingSystem.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotations")
@CrossOrigin
public class QuotationController {

    @Autowired
    private QuotationService quotationService;

    //Create quotations
    @PostMapping("/create")
    public ResponseEntity<Quotations> createQuotation(@RequestBody Quotations quotations,
                                                      @RequestParam float amount){
        try{
            Quotations createdQuotation = quotationService.createQuotations(quotations, amount);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdQuotation);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //Lấy quotation ? trong Booking
    @GetMapping("/booking/{bookId}")
    public ResponseEntity<List<Quotations>> getQuotations(@PathVariable Long quotationId){
        List<Quotations> quotations = quotationService.getQuotationsByBookID(quotationId);
        if(quotations.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(quotations);
    }

    @DeleteMapping("/{quotationId}/delete")
    public ResponseEntity<?> deleteQuotation(@PathVariable Long quotationId){
        try {
            quotationService.deleteQuotations(quotationId);
            return ResponseEntity.ok("Quotation has been finished");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Lấy amount từ bookingTourDetail
    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuotation(@PathVariable Long id){
        try{
            quotationService.updateQuotations(id);
            return ResponseEntity.ok("Amount set successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
