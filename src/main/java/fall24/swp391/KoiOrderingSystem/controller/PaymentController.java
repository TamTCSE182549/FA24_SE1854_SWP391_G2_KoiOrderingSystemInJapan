package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private IBookingService bookingService;

    //Endpoint to create PaymentTour
    @PostMapping("/create-pending")
    public ResponseEntity<Bookings> createPendingPayment(@RequestBody Bookings booking){
        Bookings newPaymentTour = bookingService.createPaymentTourPending(booking);
        return ResponseEntity.ok(newPaymentTour);
    }

    //Endpoint to confirm PaymentTour
    @PutMapping("/confirm/{id}")
    public ResponseEntity<Bookings> confirmPaymentTour(@PathVariable Long Id,
                                                       @RequestBody Bookings bookingTourDetails){
        Optional<Bookings> existingBooking = Optional.ofNullable(bookingService.updateBooking(Id, bookingTourDetails));
        return existingBooking.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Endpoint to cancel or delete PaymentTour
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Bookings> cancelPayment(@PathVariable Long Id){
        Bookings booking = new Bookings();
        booking.setId(Id);
        Bookings cancelledPaymentTour = bookingService.cancelledPaymentTour(booking);
        if(cancelledPaymentTour != null){
            return ResponseEntity.ok(cancelledPaymentTour);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{accountId}")
    public ResponseEntity<List<Bookings>> getPaymentStatus(@PathVariable Long accountId){
        List<Bookings> userPayment = bookingService.getTourBooking(accountId);
        if(userPayment.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userPayment);
    }

}
