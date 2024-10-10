package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.pojo.Quotations;
import fall24.swp391.KoiOrderingSystem.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quotations")
@CrossOrigin
public class QuotationController {

    @Autowired
    private QuotationService quotationService;

    //Lấy amount từ bookingTourDetail
    @PutMapping("/{id}/set-amount")
    public ResponseEntity<?> setAmount(@PathVariable Long id){
        try{
            quotationService.updateQuotations(id);
            return ResponseEntity.ok("Amount set successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
