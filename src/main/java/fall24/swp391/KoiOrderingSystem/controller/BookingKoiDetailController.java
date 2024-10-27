package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.model.request.BookingKoiDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.request.UpdateBookingKoiDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.BookingKoiDetailResponse;
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

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<?> updateKoiDetail(@PathVariable Long bookingId,@RequestBody List<UpdateBookingKoiDetailRequest> updateBookingKoiDetailRequest){
        List<BookingKoiDetail> updateKoiDetail = iBookingKoiDetailService.updateBookingKoiDetail(bookingId,updateBookingKoiDetailRequest);
        return ResponseEntity.ok(updateKoiDetail);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteKoiDetail(@PathVariable Long Id){
        iBookingKoiDetailService.deletebyBookingKoiDetail(Id);
        return ResponseEntity.ok("Delete detail success");
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingkoiDetail(@PathVariable Long bookingId){
        List<BookingKoiDetail> koiDetail =iBookingKoiDetailService.bookingKoiDetails(bookingId);
        return ResponseEntity.ok(koiDetail);
    }

    @PostMapping("/{bookingId}")
    public ResponseEntity<?> createKoiDetail(@PathVariable Long bookingId, @RequestBody BookingKoiDetailRequest bookingKoiDetailRequest){
        BookingKoiDetailResponse bookingKoiDetailResponse =iBookingKoiDetailService.createKoiDetail(bookingKoiDetailRequest,bookingId);
        return ResponseEntity.ok(bookingKoiDetailResponse);
    }
}
