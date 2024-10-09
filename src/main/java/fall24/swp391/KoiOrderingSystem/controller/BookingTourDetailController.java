package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import fall24.swp391.KoiOrderingSystem.service.IBookingTourDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BookingTourDetailController {

    @Autowired
    private IBookingTourDetailService iBookingTourDetailService;

    public ResponseEntity<?> updateBookingTourDetail(@RequestBody BookingTourDetail bookingTourDetail){
        BookingTourDetail bookingUpdateDetail = iBookingTourDetailService.updateBookingTourDetail(bookingTourDetail);
        return ResponseEntity.ok(bookingUpdateDetail);
    }
}
