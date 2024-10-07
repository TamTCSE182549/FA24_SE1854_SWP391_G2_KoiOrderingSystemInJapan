//package fall24.swp391.KoiOrderingSystem.controller;
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.web.bind.annotation.*;
//import com.google.api.client.json.jackson2.JacksonFactory;
//
//import java.security.Principal;
//import java.util.Collections;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/")
//@CrossOrigin(origins = "*")
//public class GoogleController {
//    private static final String GOOGLE_CLIENT_ID = "738391852199-e9cllf84bulqf7hsbgl5i7gofq1vrb8o.apps.googleusercontent.com";
//
//    @PostMapping("/google")
//    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> request) {
//        String token = request.get("token");
//
//        try {
//            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
//                    .setAudience(Collections.singletonList(GOOGLE_CLIENT_ID))
//                    .build();
//
//            GoogleIdToken idToken = verifier.verify(token);
//            if (idToken != null) {
//                GoogleIdToken.Payload payload = idToken.getPayload();
//
//                // Get user profile information from payload
//                String userId = payload.getSubject();
//                String email = payload.getEmail();
//                String name = (String) payload.get("name");
//                String pictureUrl = (String) payload.get("picture");
//                String locale = (String) payload.get("locale");
//                String familyName = (String) payload.get("family_name");
//                String givenName = (String) payload.get("given_name");
//
//                // You can now use this information to create or log in the user in your application
//                return ResponseEntity.ok("User authenticated: " + email);
//            } else {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error verifying Google token: " + e.getMessage());
//        }
//    }
//}
