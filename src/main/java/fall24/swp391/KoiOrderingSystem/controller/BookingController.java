package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.model.request.*;
import fall24.swp391.KoiOrderingSystem.model.response.*;
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


    @PostMapping("/CreateForTour/customer")
    public ResponseEntity<?> createBooking(@RequestBody BookingTourCustomRequest bookingTourCustomRequest) {
        BookingTourCustomResponse bookingTourResponse= bookingService.createBookingCustom(bookingTourCustomRequest);
        return ResponseEntity.ok(bookingTourResponse);
    }

    @PutMapping("/updateCustom/{bookingId}")
    public ResponseEntity<?>updateBooking(@RequestBody BookingTourCustomRequest bookingTourCustomRequest, @PathVariable Long bookingId){
        BookingTourCustomResponse bookingTourResponse=bookingService.updateBookingCustom(bookingTourCustomRequest,bookingId);
        return ResponseEntity.ok(bookingTourResponse);
    }

    //Get BookingForTour STAFF role
    @GetMapping("/BookingForTour")
    public ResponseEntity<?> showBookingForTour(){
        List<BookingTourResponse> bookingTourResponses = bookingService.bookingForTour();
        return ResponseEntity.ok(bookingTourResponses);
    }

    @GetMapping("/BookingForTourAccepted")
    public ResponseEntity<?> showBookingForTourAccept(){
        List<BookingTourResponse> bookingTourResponses = bookingService.bookingForTourAccepted();
        return ResponseEntity.ok(bookingTourResponses);
    }

    @GetMapping("/paymentUrl/{bookingId}")
    public ResponseEntity<?> getPaymentUrl(@PathVariable Long bookingId) throws Exception {
        String url = bookingService.createUrl(bookingId);
        return ResponseEntity.ok(url);
    }
    @PutMapping("/payment/confirm")
    public ResponseEntity<?> updateBooking(@RequestBody PaymentRequest paymentRequest) {
        bookingService.updatePayment(paymentRequest);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/BookingForTour/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable Long bookingId){
        BookingTourRes bookingTourResponses = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(bookingTourResponses);
    }

    @GetMapping("/BookingForTourUpdateAccept/{bookingId}")
    public ResponseEntity<?> updateBookingAcceptById(@PathVariable Long bookingId){
        bookingService.acceptBooking(bookingId);
        return new ResponseEntity<>("Accepted success", HttpStatus.OK);
    }

    @GetMapping("/listBookingTourResponse")
    public ResponseEntity<List<BookingTourResponse>> getTourBookingResponse() {
        List<BookingTourResponse> bookings = bookingService.getTourBookingResponse();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/listBookingTourOtherStatus")
    public ResponseEntity<List<BookingTourResponse>> getTourBookingOtherStatus() {
        List<BookingTourResponse> bookings = bookingService.getBookingResponseComplete();
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

    @PutMapping("/admin/updateResponseCusFormStaff/Customize")
    public ResponseEntity<?> updateBookingAndResponseCus(@RequestBody BookingUpdateRequestStaff bookingUpdateRequestStaff) {
        BookingTourResponse bookingTourResponse = bookingService.responseUpdateCusForStaff(bookingUpdateRequestStaff);
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

    @PutMapping("/delete/koi/{bookingId}")
    public ResponseEntity<?> deleteBookingKoi(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return new ResponseEntity<>("Delete booking complete", HttpStatus.OK);
    }

    @GetMapping("/koi/list/customer")
    public ResponseEntity<?> getKoiBookings() {
        List<BookingResponseDetail> bookings = bookingService.getKoiBookingById();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/koi/list/staff")
    public ResponseEntity<?> getKoiBookingsByCreateBy() {
        List<BookingResponseDetail> bookings = bookingService.getKoiBookingByCreateBy();
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/koi/list/delivery")
    public ResponseEntity<?> getAllBookingKoiForDelivery() {
        List<BookingResponseDetail> bookings = bookingService.getKoiBookingForDelivery();
        return ResponseEntity.ok(bookings);
    }


    @GetMapping("/BookingForKoi")
    public ResponseEntity<?> showBookingForKoi(){
        List<BookingResponseDetail> bookingTourResponses = bookingService.getKoiBooking();
        return ResponseEntity.ok(bookingTourResponses);
    }

    @GetMapping("/ViewDetail/{bookingId}")
    public ResponseEntity<?> viewDetail(@PathVariable Long bookingId){
        KoiDetailResponseInBoooking bookingResponse = bookingService.viewDetailBooking(bookingId);
        return ResponseEntity.ok(bookingResponse);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<?> updateBookingId(@PathVariable Long bookingId, @RequestBody BookingUpdate bookingUpdate){
        BookingTourResponse bookingResponse = bookingService.updateKoiBooking(bookingId,bookingUpdate);
        return  ResponseEntity.ok(bookingResponse);
    }

    @PutMapping("/update/status/{bookingId}")
    public ResponseEntity<?> updateStatus(@PathVariable Long bookingId){
        BookingTourResponse bookingResponse = bookingService.updateStatus(bookingId);
        return  ResponseEntity.ok(bookingResponse);
    }

    @GetMapping("/listbooking/shipping")
    public ResponseEntity<?> showBookingForKoiShipping(){
        List<BookingTourResponse> bookingTourResponses = bookingService.getKoiBookingShipping();
        return ResponseEntity.ok(bookingTourResponses);
    }

    @GetMapping("/AllBooking")
    public ResponseEntity<?> getAllBooking(){
        List<BookingTourResponse> bookingTourResponses = bookingService.getAllBooking();
        return ResponseEntity.ok(bookingTourResponses);
    }

    @GetMapping("/AllBookingStatus/{paymentStatus}")
    public ResponseEntity<?> getAllBookingStatus(@PathVariable String paymentStatus){
        List<BookingTourResponse> bookingTourResponses = bookingService.showAllBookingStatus(paymentStatus);
        return ResponseEntity.ok(bookingTourResponses);
    }

    @GetMapping("/BookingForTourStatus/{paymentStatus}")
    public ResponseEntity<?> getBookingTourStatus(@PathVariable String paymentStatus){
        List<BookingTourResponse> bookingTourResponses = bookingService.showBookingTourStatus(paymentStatus);
        return ResponseEntity.ok(bookingTourResponses);
    }

    @GetMapping("/BookingForKoiStatus/{paymentStatus}")
    public ResponseEntity<?> getBookingKoiStatus(@PathVariable String paymentStatus){
        List<BookingTourResponse> bookingTourResponses = bookingService.showBookingKoiStatus(paymentStatus);
        return ResponseEntity.ok(bookingTourResponses);
    }
}
