package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.model.request.KoiOfFarmRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiOfFarmResponse;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import fall24.swp391.KoiOrderingSystem.service.IKoiOfFarmService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/koi-of-farm")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "api")
public class KoiOfFarmController {
    @Autowired
    private IKoiOfFarmService koiOfFarmService;

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<KoiOfFarmResponse>> getKoiOfFarmByFarmId(@PathVariable Long farmId) {
        List<KoiOfFarmResponse> koiOfFarmResponses = koiOfFarmService.findKoiOfFarmByFarmId(farmId);
        if (koiOfFarmResponses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(koiOfFarmResponses);
    }

    @PostMapping("/create")
    public ResponseEntity<KoiOfFarm> addKoiToFarm(@RequestBody KoiOfFarmRequest koiOfFarmRequest) {
        try {
            KoiOfFarm koiOfFarm = koiOfFarmService.addKoiToFarm(koiOfFarmRequest);
            return new ResponseEntity<>(koiOfFarm, HttpStatus.CREATED);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PutMapping("/updateQuantity/{farmId}/{koiId}")
//    public ResponseEntity<KoiOfFarm> updateKoiQuantity(
//            @PathVariable Long farmId,
//            @PathVariable Long koiId,
//            @RequestParam int newQuantity) {
//        KoiOfFarm updatedKoiOfFarm = koiOfFarmService.updateKoiQuantity(farmId, koiId, newQuantity);
//        return ResponseEntity.ok(updatedKoiOfFarm);
//    }
}
