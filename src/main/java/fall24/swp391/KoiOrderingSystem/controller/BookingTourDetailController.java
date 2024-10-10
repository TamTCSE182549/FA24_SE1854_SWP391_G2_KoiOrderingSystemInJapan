package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.pojo.BookingTourDetail;
import fall24.swp391.KoiOrderingSystem.service.IBookingTourDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/BookingTourDetail")
public class BookingTourDetailController {

    @Autowired
    private IBookingTourDetailService iBookingTourDetailService;

    //create BookingTourDetail được tạo trực tiếp khi add booking lần đầu tiên

    @PutMapping("/update")
    public ResponseEntity<?> updateBookingTourDetail(@RequestBody BookingTourDetail bookingTourDetail){
        BookingTourDetail bookingUpdateDetail = iBookingTourDetailService.updateBookingTourDetail(bookingTourDetail);
        return ResponseEntity.ok(bookingUpdateDetail);
    }

    @DeleteMapping("/{bookingTourDetailID}")
    public ResponseEntity<?> deleteBookingTourDetail(@PathVariable Long bookingTourDetailID){
        iBookingTourDetailService.deleteBookingTourDetail(bookingTourDetailID);
        return ResponseEntity.ok("Delete detail of Booking complete");
    }

    @GetMapping("/{bookingID}")
    public ResponseEntity<?> viewBookingTourDetail(@PathVariable Long bookingID){
        List<BookingTourDetail> bookingTourDetailList = iBookingTourDetailService.bookingTourDetails(bookingID);
        return ResponseEntity.ok(bookingTourDetailList);
    }
}
