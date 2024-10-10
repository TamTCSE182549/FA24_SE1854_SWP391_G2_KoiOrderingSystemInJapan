package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.pojo.Checkin;
import fall24.swp391.KoiOrderingSystem.service.ICheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/checkins")
public class CheckinController {

    @Autowired
    private ICheckinService checkinService;


    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getChekinByBookingId(@PathVariable Long bookingId) {
        List<Checkin> checkins = checkinService.getChekinByBookingId(bookingId);
        return ResponseEntity.ok(checkins);
    }


    @PostMapping("/{bookingId}")
    public ResponseEntity<?> createCheckin(@RequestBody Checkin checkin,@PathVariable Long bookingId) {
        Checkin createdCheckin = checkinService.createCheckin(checkin,bookingId);
        return new ResponseEntity<>(createdCheckin, HttpStatus.CREATED);
    }


    @PutMapping("/{checkinId}")
    public ResponseEntity<?> updateCheckin(@PathVariable Long checkinId, @Validated @RequestBody Checkin checkinDetail) {
        Checkin checkin = checkinService.updateCheckin(checkinId,checkinDetail);
        return new ResponseEntity<>(checkin,HttpStatus.OK);
    }


    @DeleteMapping("/{checkId}")
    public ResponseEntity<?> deleteCheckin(@PathVariable Long id) {
       checkinService.deleteCheckin(id);
       return ResponseEntity.ok("Delete Successfull");
        }

}
