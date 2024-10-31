package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.model.request.DepositRequest;
import fall24.swp391.KoiOrderingSystem.model.response.DepositRespone;
import fall24.swp391.KoiOrderingSystem.pojo.Deposit;
import fall24.swp391.KoiOrderingSystem.service.IDepositService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/deposit")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "api")
public class DepositController {

    @Autowired
    private IDepositService depositService;

    @PostMapping("/{bookingId}")
    public ResponseEntity<?> createDeposit(@RequestBody DepositRequest depositRequest, @PathVariable Long bookingId){
        DepositRespone createDeposit = depositService.createDeposit(depositRequest,bookingId);
        return  new ResponseEntity<>(createDeposit,HttpStatus.CREATED);
    }


    @PutMapping("/{ID}")
    public ResponseEntity<?> updateDeposit(@PathVariable Long ID,@RequestBody DepositRequest depositRequest){
        DepositRespone deposit = depositService.updateDeposit(ID,depositRequest);
        return new ResponseEntity<>(deposit,HttpStatus.OK);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?>getDeposit(@PathVariable Long bookingId){
        DepositRespone list = depositService.getDepositByBookingId(bookingId);
        if(list==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking ID not found");
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @DeleteMapping("/{ID}")
    public ResponseEntity<?>deleteDeposit(@PathVariable Long ID) {
        DepositRespone isDeleted = depositService.deleteById(ID);
        return ResponseEntity.ok(isDeleted);
        }

}
