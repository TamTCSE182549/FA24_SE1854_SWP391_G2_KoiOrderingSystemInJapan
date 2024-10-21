package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.model.request.KoiFarmDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiFarmDetailResponse;
import fall24.swp391.KoiOrderingSystem.service.IKoiFarmDetailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/KoiFarmDetail")
@SecurityRequirement(name = "api")
public class KoiFarmDetailController {

    @Autowired
    private IKoiFarmDetailService iKoiFarmDetailService;

    @PostMapping("/create")
    public ResponseEntity<?> createFarmDetail(KoiFarmDetailRequest koiFarmDetailRequest){
        KoiFarmDetailResponse koiFarmDetailResponse = iKoiFarmDetailService.createFarmDetailRes(koiFarmDetailRequest);
        return ResponseEntity.ok(koiFarmDetailResponse);
    }

    @GetMapping("farm-detail/{farmID}")
    public ResponseEntity<?> showFarmDetailByFarmID(@PathVariable Long farmID){
        List<KoiFarmDetailResponse> koiFarmDetailResponses = iKoiFarmDetailService.farmDetailByFarm(farmID);
        return ResponseEntity.ok(koiFarmDetailResponses);
    }

    @PutMapping("update/{farmDetailID}")
    public ResponseEntity<?> updateFarmDetail(@PathVariable Long farmDetailID,
                                              @RequestParam KoiFarmDetailRequest koiFarmDetailRequest){
        iKoiFarmDetailService.updateKoiFarmDetailRes(farmDetailID,koiFarmDetailRequest);
        return ResponseEntity.ok("Update Farm Detail complete");
    }

    @DeleteMapping("delete/{farmDetailID}")
    public ResponseEntity<?> deleteFarmDetail(@PathVariable Long farmDetailID){
        iKoiFarmDetailService.deleteFarmDetail(farmDetailID);
        return ResponseEntity.ok("Update Farm Detail complete");
    }
}
