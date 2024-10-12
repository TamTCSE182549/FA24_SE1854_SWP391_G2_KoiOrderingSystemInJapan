package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.model.request.KoiRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import fall24.swp391.KoiOrderingSystem.service.IKoisService;
import fall24.swp391.KoiOrderingSystem.service.KoiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kois/")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "api")
public class KoiController {

    @Autowired
    private KoiService iKoisService;


    @PostMapping("/{farmId}")
    public ResponseEntity<?> creatKoi(@PathVariable Long farmId, @RequestBody KoiRequest koiRequest){
        Kois kois =iKoisService.createKois(koiRequest, farmId);
        return ResponseEntity.status(HttpStatus.CREATED).body(kois);
    }
}
