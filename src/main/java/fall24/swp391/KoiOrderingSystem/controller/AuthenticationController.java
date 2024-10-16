package fall24.swp391.KoiOrderingSystem.controller;
import fall24.swp391.KoiOrderingSystem.model.request.*;
import fall24.swp391.KoiOrderingSystem.model.response.AccountResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/")
@SecurityRequirement(name = "api")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) throws Exception {
        AccountResponse newAccount = authenticationService.register(registerRequest);
        return ResponseEntity.ok(newAccount);
    }

    @PutMapping("profile/update")
    public ResponseEntity<?> update(@Valid @RequestBody AccountUpdateRequest accountUpdateRequest
//                                    ,@RequestHeader("Authorization") String authHeader
    ) throws Exception {
         if(authenticationService.updateAccount(accountUpdateRequest))
        return ResponseEntity.ok("Account updated successfully");
         else
             return ResponseEntity.badRequest().body("Account update failed");
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody AccountRequest accountRequest){
        AccountResponse newAccount = authenticationService.login(accountRequest);
        return ResponseEntity.ok(newAccount);
    }

    @GetMapping("account")
    public ResponseEntity<?> getAllAccount(){
        List<Account> accountList = authenticationService.getAllAccount();
        return ResponseEntity.ok(accountList);
    }

    @PostMapping("forgot-password")
    public ResponseEntity forgotPassword(@Valid @RequestBody ForgotPassRequest forgotPassRequest){
        authenticationService.forgotPassword(forgotPassRequest);
        return ResponseEntity.ok("Check email to reset password!");
    }

    @PostMapping("reset-password")
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        authenticationService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok("Reset Password successfully");
    }
}
