package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.component.Email;
import fall24.swp391.KoiOrderingSystem.model.EmailDetail;
import fall24.swp391.KoiOrderingSystem.model.response.NofiticationRespone;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.repo.IAccountRepository;
import fall24.swp391.KoiOrderingSystem.repo.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private Email emailService;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmailNofi(@RequestBody NofiticationRespone nofiticationRespone){
        try{
            String receiver = nofiticationRespone.getEmail().getReceiver().getEmail();
            if(receiver == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Receiver not found");
            }

            EmailDetail emailDetail = new EmailDetail();
            emailService.sendEmail(emailDetail);
            return ResponseEntity.ok("Email sent successfully!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send Email!");
        }
    }
}
