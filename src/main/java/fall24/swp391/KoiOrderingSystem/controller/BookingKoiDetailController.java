package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.pojo.BookingKoiDetail;
import fall24.swp391.KoiOrderingSystem.service.IBookingKoiDetailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/BookingKoiDetail")
@SecurityRequirement(name = "api")
public class BookingKoiDetailController {

    @Autowired
    private IBookingKoiDetailService iBookingKoiDetailService;

    @PutMapping("/update")
    public ResponseEntity<?> updateKoiDetail(@RequestBody BookingKoiDetail bookingKoiDetail){
        BookingKoiDetail updateKoiDetail = iBookingKoiDetailService.updateBookingKoiDetail(bookingKoiDetail);
        return ResponseEntity.ok(updateKoiDetail);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteKoiDetail(@PathVariable Long Id){
        iBookingKoiDetailService.deletebyBookingKoiDetail(Id);
        return ResponseEntity.ok("Delete detail success");
    }

    @GetMapping("/{Id}")
    public ResponseEntity<?> getBookingkoiDetail(@PathVariable Long Id){
        List<BookingKoiDetail> koiDetail =iBookingKoiDetailService.bookingKoiDetails(Id);
        return ResponseEntity.ok(koiDetail);
    }
}
