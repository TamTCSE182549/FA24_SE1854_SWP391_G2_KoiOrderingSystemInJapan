package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.pojo.Deposit;
import fall24.swp391.KoiOrderingSystem.service.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deposit")
public class DepositController {

    @Autowired
    private IDepositService depositService;

    @PostMapping
    public ResponseEntity<?> createDeposit(@RequestBody Deposit deposit){
        Deposit createDeposit = depositService.createDeposit(deposit);
        return  new ResponseEntity<>(createDeposit,HttpStatus.CREATED);
    }


    @PutMapping("/{ID}")
    public ResponseEntity<?> updateDeposit(@PathVariable Long ID,@RequestBody Deposit depositDetail){
        Deposit deposit = depositService.updateDeposit(ID,depositDetail);
        return new ResponseEntity<>(deposit,HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?>getDeposit(@PathVariable Long bookingId){
        List<Deposit> list = depositService.getDepositByBookingId(bookingId);
        if(list==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking ID not found");
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @DeleteMapping("/{ID}")
    public ResponseEntity<?>deleteDeposit(@PathVariable Long ID) {
        Boolean isDeleted = depositService.deleteById(ID);
        if (isDeleted) {
            return ResponseEntity.ok("Delete successful");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deposit Not Found");
        }
    }
}
