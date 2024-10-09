package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    // Create a new booking
    @PostMapping("/create/{tourID}")
    public ResponseEntity<Bookings> createBooking(@PathVariable Long tourID,
                                                  @RequestBody Bookings booking,
                                                  @RequestParam int participants) {
        Bookings createdBooking = bookingService.createTourBooking(tourID, booking, participants);
        return ResponseEntity.ok(createdBooking);
    }
//
//    // Get a booking by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Bookings> getBookingById(@PathVariable Long id) {
//        Optional<Bookings> booking = bookingService.getBookingById(id);
//        return booking.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
    // Get all bookings
    @GetMapping("/list/{accountID}")
    public ResponseEntity<List<Bookings>> getTourBookings(@PathVariable Long accountID) {
        List<Bookings> bookings = bookingService.getTourBooking(accountID);
        return ResponseEntity.ok(bookings);
    }

    // Update an existing booking
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody Bookings bookingDetails) {
        bookingService.updateBooking(id, bookingDetails);
        return new ResponseEntity<>("Update Complete", HttpStatus.OK);
    }

    // Delete a booking by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Bookings> deleteBooking(@PathVariable Long id) {
        Bookings bookings = bookingService.deleteBooking(id);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}
