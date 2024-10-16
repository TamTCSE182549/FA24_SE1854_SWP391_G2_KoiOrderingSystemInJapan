package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.model.request.BookingKoiRequest;
import fall24.swp391.KoiOrderingSystem.model.request.BookingTourRequest;
import fall24.swp391.KoiOrderingSystem.model.request.BookingUpdateRequestCus;
import fall24.swp391.KoiOrderingSystem.model.request.BookingUpdateRequestStaff;
import fall24.swp391.KoiOrderingSystem.model.response.BookingTourResponse;
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
@CrossOrigin(origins = "*")
@RequestMapping("/bookings")
@SecurityRequirement(name = "api")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private ModelMapper modelMapper;

    // Create a new booking for Customer
    @PostMapping("/CreateForTour")
    public ResponseEntity<?> createBooking(@RequestBody BookingTourRequest bookingTourRequest) {
        BookingTourResponse bookingTourResponse = bookingService.createTourBooking(bookingTourRequest);
        return ResponseEntity.ok(bookingTourResponse);
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
    //Get BookingForTour STAFF role
    @GetMapping("/BookingForTour")
    public ResponseEntity<?> showBookingForTour(){
        List<BookingTourResponse> bookingTourResponses = bookingService.bookingForTour();
        return ResponseEntity.ok(bookingTourResponses);
    }

    // Get all bookings
//    @GetMapping("/list/{accountID}")
//    public ResponseEntity<List<Bookings>> getTourBookings(@PathVariable Long accountID) {
//        List<Bookings> bookings = bookingService.getTourBooking(accountID);
//        return ResponseEntity.ok(bookings);
//    }

    @GetMapping("/listBookingTourResponse")
    public ResponseEntity<List<BookingTourResponse>> getTourBookingResponse() {
        List<BookingTourResponse> bookings = bookingService.getTourBookingResponse();
        return ResponseEntity.ok(bookings);
    }

    // Update an existing booking
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody Bookings bookingDetails) {
//        bookingService.updateTourBooking(id, bookingDetails);
//        return new ResponseEntity<>("Update Complete", HttpStatus.OK);
//    }

//    @PutMapping("/updateResponseForm")
//    public ResponseEntity<?> updateBookingAndResponse(@RequestBody Bookings bookingDetails) {
//        BookingTourResponse bookingTourResponse = bookingService.updateTourBookingResponse(bookingDetails);
//        return new ResponseEntity<>(bookingTourResponse, HttpStatus.OK);
//    }

    @PutMapping("/updateResponseFormStaff")
    public ResponseEntity<?> updateBookingAndResponse(@RequestBody BookingUpdateRequestStaff bookingUpdateRequestStaff) {
        BookingTourResponse bookingTourResponse = bookingService.responseUpdateForStaff(bookingUpdateRequestStaff);
        return new ResponseEntity<>(bookingTourResponse, HttpStatus.OK);
    }

    @PutMapping("/updateResponseFormCustomer")
    public ResponseEntity<?> updateBookingAndResponseCus(@RequestBody BookingUpdateRequestCus bookingUpdateRequestCus) {
        BookingTourResponse bookingTourResponse = bookingService.bookingUpdatePaymentMethod(bookingUpdateRequestCus);
        return new ResponseEntity<>(bookingTourResponse, HttpStatus.OK);
    }
    // Delete a booking by ID

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Bookings> deleteBooking(@PathVariable Long id) {
//        Bookings bookings = bookingService.deleteBooking(id);
//        return new ResponseEntity<>(bookings, HttpStatus.OK);
//    }



    @PostMapping("/koi/create/{bookingId}")
    public ResponseEntity<?> createKoiBooking(@RequestBody BookingKoiRequest bookingKoiRequest,@PathVariable Long bookingId) throws Exception{
        BookingTourResponse bookingResponse = bookingService.createKoiBooking(bookingKoiRequest,bookingId);
        return ResponseEntity.ok(bookingResponse);
    }
    @DeleteMapping("/manager/delete/{bookingId}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBookingForManager(bookingId);
        return new ResponseEntity<>("Delete booking complete", HttpStatus.OK);
    }

     

//    @PutMapping("/koi/{id}")
//    public ResponseEntity<?> updateKoiBooking(@PathVariable Long id,@RequestBody Bookings bookings){
//        bookingService.updateKoiBooking(id,bookings);
//        return new ResponseEntity<>("Update Complete",HttpStatus.OK);
//    }

    @GetMapping("/koi/list/{accountID}")
    public ResponseEntity<?> getKoiBookings(@PathVariable Long accountID) {
        List<BookingTourResponse> bookings = bookingService.getKoiBookingById(accountID);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/BookingForKoi")
    public ResponseEntity<?> showBookingForKoi(){
        List<BookingTourResponse> bookingTourResponses = bookingService.getKoiBooking();
        return ResponseEntity.ok(bookingTourResponses);
    }
//    @DeleteMapping("/deleteResponseForm/{id}")
//    public ResponseEntity<BookingTourResponse> deleteBookingResponse(@PathVariable Long id) {
//        BookingTourResponse bookingTourResponse = bookingService.deleteBookingResponse(id);
//        return new ResponseEntity<>(bookingTourResponse, HttpStatus.OK);
//
//    }
}
