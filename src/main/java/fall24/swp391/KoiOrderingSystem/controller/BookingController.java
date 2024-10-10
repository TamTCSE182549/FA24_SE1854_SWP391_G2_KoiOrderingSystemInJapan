package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.model.request.BookingRequest;
import fall24.swp391.KoiOrderingSystem.model.response.BookingResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.service.IBookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/bookings")
@SecurityRequirement(name = "api")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private ModelMapper modelMapper;

    // Create a new booking
    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest bookingRequest) throws Exception {
        BookingResponse bookingResponse = bookingService.createTourBooking(bookingRequest);
        return ResponseEntity.ok(bookingResponse);
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
        bookingService.updateTourBooking(id, bookingDetails);
        return new ResponseEntity<>("Update Complete", HttpStatus.OK);
    }

    // Delete a booking by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Bookings> deleteBooking(@PathVariable Long id) {
        Bookings bookings = bookingService.deleteBooking(id);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
}
