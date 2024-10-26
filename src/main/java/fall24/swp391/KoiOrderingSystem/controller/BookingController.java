package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.model.request.BookingKoiRequest;
import fall24.swp391.KoiOrderingSystem.model.request.BookingTourRequest;
import fall24.swp391.KoiOrderingSystem.model.request.BookingUpdateRequestCus;
import fall24.swp391.KoiOrderingSystem.model.request.BookingUpdateRequestStaff;
import fall24.swp391.KoiOrderingSystem.model.response.BookingTourResponse;
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

    //Get BookingForTour STAFF role
    @GetMapping("/BookingForTour")
    public ResponseEntity<?> showBookingForTour(){
        List<BookingTourResponse> bookingTourResponses = bookingService.bookingForTour();
        return ResponseEntity.ok(bookingTourResponses);
    }

    @GetMapping("/listBookingTourResponse")
    public ResponseEntity<List<BookingTourResponse>> getTourBookingResponse() {
        List<BookingTourResponse> bookings = bookingService.getTourBookingResponse();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity<List<BookingTourResponse>> getTourBookingResponseForDashBoard() {
        List<BookingTourResponse> bookings = bookingService.getBookingResponseForDashBoard();
        return ResponseEntity.ok(bookings);
    }

    @PutMapping("/admin/updateResponseFormStaff")
    public ResponseEntity<?> updateBookingAndResponse(@RequestBody BookingUpdateRequestStaff bookingUpdateRequestStaff) {
        BookingTourResponse bookingTourResponse = bookingService.responseUpdateForStaff(bookingUpdateRequestStaff);
        return new ResponseEntity<>(bookingTourResponse, HttpStatus.OK);
    }

    @PutMapping("/updateResponseFormCustomer")
    public ResponseEntity<?> updateBookingAndResponseCus(@RequestBody BookingUpdateRequestCus bookingUpdateRequestCus) {
        BookingTourResponse bookingTourResponse = bookingService.bookingUpdatePaymentMethod(bookingUpdateRequestCus);
        return new ResponseEntity<>(bookingTourResponse, HttpStatus.OK);
    }

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

    @PutMapping("/delete/{bookingId}")
    public ResponseEntity<String> deleteBookingFosCustomer(@PathVariable Long bookingId) {
        bookingService.deleteBookingResponse(bookingId);
        return new ResponseEntity<>("Delete booking complete", HttpStatus.OK);
    }

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
}
