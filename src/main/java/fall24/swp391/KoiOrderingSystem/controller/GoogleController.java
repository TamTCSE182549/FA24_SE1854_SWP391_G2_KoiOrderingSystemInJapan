package fall24.swp391.KoiOrderingSystem.controller;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import fall24.swp391.KoiOrderingSystem.model.request.GoogleRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*")
public class GoogleController {
    private static final String GOOGLE_CLIENT_ID = "738391852199-e9cllf84bulqf7hsbgl5i7gofq1vrb8o.apps.googleusercontent.com";

    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> body) {
        try {
            String token = body.get("token"); // Extract the token from the JSON object
            if (token == null) {
                return ResponseEntity.badRequest().body("Token is missing");
            }
            GoogleIdTokenVerifier verifier =
                    new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                            .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
                            .build();

            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Get user profile information from payload
                String email = payload.getEmail();
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");
                GoogleRequest googleRequest = new GoogleRequest(email,familyName,givenName);
                String accessToken = authenticationService.loginOrRegisterGoogle(googleRequest);
                // You can now use this information to create or log in the user in your application
                return ResponseEntity.ok(accessToken);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error verifying Google token: " + e.getMessage());
        }
    }

}
