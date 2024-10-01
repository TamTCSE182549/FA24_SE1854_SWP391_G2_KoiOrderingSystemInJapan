package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/bookings")
public class BookingController {

//    @Autowired
//    private IBookingService bookingService;
//
//    // Create a new booking
//    @PostMapping
//    public ResponseEntity<Bookings> createBooking(@RequestBody Bookings booking) {
//        Bookings createdBooking = bookingService.createBooking(booking);
//        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
//    }
//
//    // Get a booking by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Bookings> getBookingById(@PathVariable Long id) {
//        Optional<Bookings> booking = bookingService.getBookingById(id);
//        return booking.map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    // Get all bookings
//    @GetMapping
//    public ResponseEntity<List<Bookings>> getAllBookings() {
//        List<Bookings> bookings = bookingService.getAllBookings();
//        return ResponseEntity.ok(bookings);
//    }
//
//    // Update an existing booking
//    @PutMapping("/{id}")
//    public ResponseEntity<Bookings> updateBooking(@PathVariable Long id, @RequestBody Bookings bookingDetails) {
//        Bookings updatedBooking = bookingService.updateBooking(id, bookingDetails);
//        return updatedBooking != null ? ResponseEntity.ok(updatedBooking)
//                : ResponseEntity.notFound().build();
//    }
//
//    // Delete a booking by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Boolean> deleteBooking(@PathVariable Long id) {
//        boolean deleted = bookingService.deleteBooking(id);
//        return deleted ? ResponseEntity.ok(true)
//                : ResponseEntity.notFound().build();
//    }
}
