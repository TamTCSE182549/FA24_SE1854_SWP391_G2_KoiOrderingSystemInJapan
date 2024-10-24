package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.model.request.KoiRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import fall24.swp391.KoiOrderingSystem.service.IKoisService;
import fall24.swp391.KoiOrderingSystem.service.KoiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kois/")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "api")
public class KoiController {

    @Autowired
    private KoiService iKoisService;


    @PostMapping
    public ResponseEntity<?> creatKoi( @RequestBody KoiRequest koiRequest){
        KoiResponse kois =iKoisService.createKois(koiRequest);
        return ResponseEntity.status(HttpStatus.OK).body(kois);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<?> updateKois(@PathVariable Long Id,@RequestBody KoiRequest koiRequest){
        KoiResponse kois = iKoisService.updateKoi(Id,koiRequest);
        return ResponseEntity.ok(kois);
    }

    @GetMapping("/getby/{Id}")
    public ResponseEntity<?> getKoisById(@PathVariable Long Id){
        KoiResponse kois = iKoisService.getKoiById(Id);
        return ResponseEntity.ok(kois);
    }

    @GetMapping("all/active")
    public ResponseEntity<?> getAllKoisActive(){
        List<KoiResponse> koisList =iKoisService.findAllActive();
        return ResponseEntity.ok(koisList);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllKois(){
        List<KoiResponse> koisList =iKoisService.findAll();
        return ResponseEntity.ok(koisList);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteKois(@PathVariable Long Id){
        KoiResponse koiResponse =iKoisService.deletebyId(Id);
        return ResponseEntity.ok(koiResponse);
    }
}
