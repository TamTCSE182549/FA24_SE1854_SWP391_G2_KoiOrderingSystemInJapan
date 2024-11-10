package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.model.request.FeedbackRequest;
import fall24.swp391.KoiOrderingSystem.model.response.FeedbackResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Feedback;
import fall24.swp391.KoiOrderingSystem.service.FeedbackService;
import fall24.swp391.KoiOrderingSystem.service.IFeedbackService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "api")
public class FeedbackController {
    @Autowired
    IFeedbackService feedbackService;
    @PostMapping("/create")
    public ResponseEntity<?> createFeedback(@RequestBody FeedbackRequest feedbackRequest){
        FeedbackResponse feedback = feedbackService.createFeedback(feedbackRequest);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllFeedback(){
        return ResponseEntity.ok(feedbackService.getAllFeedback());
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<?> getFeedbackByBookingId(@PathVariable Long bookingId){
        return ResponseEntity.ok(feedbackService.getFeedbackByBooking(bookingId));
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getFeedbackByCustomer(){
        return ResponseEntity.ok(feedbackService.getFeedbackByCustomer());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFeedback(@PathVariable Long id, @RequestBody FeedbackRequest feedbackRequest){
        FeedbackResponse feedbackResponse = feedbackService.updateFeedback(feedbackRequest,id);
        return ResponseEntity.ok(feedbackResponse);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id){
        feedbackService.deleteFeedback(id);
        return ResponseEntity.ok("Delete Feedback complete");
    }
}
