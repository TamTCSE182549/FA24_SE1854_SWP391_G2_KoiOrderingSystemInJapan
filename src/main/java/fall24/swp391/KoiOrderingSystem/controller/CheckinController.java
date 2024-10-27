package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.model.request.CheckinRequest;
import fall24.swp391.KoiOrderingSystem.model.response.CheckinResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Checkin;
import fall24.swp391.KoiOrderingSystem.service.ICheckinService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@SecurityRequirement(name = "api")
@RestController
@RequestMapping("/checkins")
public class CheckinController {

    @Autowired
    private ICheckinService checkinService;


    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getChekinByBookingId(@PathVariable Long bookingId) {
        List<CheckinResponse> checkins = checkinService.getChekinByBookingId(bookingId);
        return ResponseEntity.ok(checkins);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<CheckinResponse>> getCheckinByAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(checkinService.getCheckinByAccount(accountId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getCheckin() {
        List<CheckinResponse> checkins = checkinService.getAllCheckin();
        return ResponseEntity.ok(checkins);
    }


    @PostMapping("/{bookingId}")
    public ResponseEntity<?> createCheckin(@RequestBody CheckinRequest checkinRequest, @PathVariable Long bookingId) {
        CheckinResponse createdCheckin = checkinService.createCheckin(checkinRequest,bookingId);
        return new ResponseEntity<>(createdCheckin, HttpStatus.OK);
    }


    @PutMapping("/{checkinId}")
    public ResponseEntity<?> updateCheckin(@PathVariable Long checkinId, @Validated @RequestBody CheckinRequest checkinRequest) {
        CheckinResponse checkin = checkinService.updateCheckin(checkinId,checkinRequest);
        return new ResponseEntity<>(checkin,HttpStatus.OK);
    }
    @PutMapping("/status/{checkinId}")
    public ResponseEntity<?> updateCheckinStatus(@PathVariable Long checkinId) {
        CheckinResponse checkin = checkinService.updateCheckinStatus(checkinId);
        return new ResponseEntity<>(checkin,HttpStatus.OK);
    }

    @DeleteMapping("/{checkId}")
    public ResponseEntity<?> deleteCheckin(@PathVariable Long checkId) {
       checkinService.deleteCheckin(checkId);
       return ResponseEntity.ok("Delete Successfull");
        }

}
