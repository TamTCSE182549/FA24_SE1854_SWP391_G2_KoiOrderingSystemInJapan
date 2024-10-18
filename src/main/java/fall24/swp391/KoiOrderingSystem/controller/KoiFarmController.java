package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.model.request.KoiFarmRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiFarmResponse;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import fall24.swp391.KoiOrderingSystem.service.IKoiFarmsService;
import fall24.swp391.KoiOrderingSystem.service.KoiOfFarmService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/koi-farm")
@SecurityRequirement(name = "api")
public class KoiFarmController {

    @Autowired
    private IKoiFarmsService iKoiFarmsService;

    @Autowired
    private KoiOfFarmService koiOfFarmService;

    @PostMapping("/create")
    public ResponseEntity<?> createKoiFarmRes(@RequestBody KoiFarmRequest koiFarmRequest) {
        KoiFarmResponse koiFarmResponse = iKoiFarmsService.createKoiFarm(koiFarmRequest);
        return new ResponseEntity<>("Create Koi_Farm: "+ koiFarmRequest.getFarmName() + "Success\n" + koiFarmResponse, HttpStatus.CREATED);
    }

    @GetMapping("/list-farm/{id}")
    public ResponseEntity<?> getFarm(@PathVariable Long id){
        List<KoiFarmResponse> koiFarmResponseList = iKoiFarmsService.getFarmById(id);
        if(koiFarmResponseList == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Farm not Found");
        }
        return new ResponseEntity<>(koiFarmResponseList,HttpStatus.OK);
    }

    @GetMapping("/list-farm")
    public ResponseEntity<?> showKoiFarm(){
        List<KoiFarms> koiFarms = iKoiFarmsService.listKoiFarm();
        if(koiFarms.isEmpty()){
            return ResponseEntity.ok("Empty List");
        } else {
            return ResponseEntity.ok(koiFarms);
        }
    }

    @GetMapping("/list-farm-res")
    public ResponseEntity<List<KoiFarmResponse>> getAllKoiFarms() {
        List<KoiFarmResponse> koiFarms = iKoiFarmsService.getAllKoiFarms();
        if (koiFarms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(koiFarms);
    }

    @GetMapping("/list-farm-active")
    public ResponseEntity<List<KoiFarmResponse>> getKoiFarmActive() {
        List<KoiFarmResponse> koiFarms = iKoiFarmsService.getFarmIsActive();
        if (koiFarms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(koiFarms);
    }

    @GetMapping("/showKoiFarmByName/{farmName}")
    public ResponseEntity<?> showFarmByName(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "0") int size,
                                            @PathVariable String farmName){
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", iKoiFarmsService.showFarmByName(page, size, farmName).getTotalPages());
        response.put("pageNumber", iKoiFarmsService.showFarmByName(page, size, farmName).getNumber());
        response.put("content", iKoiFarmsService.showFarmByName(page, size, farmName).get());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateKoiFarms(@PathVariable Long id,
                                            @RequestBody KoiFarmRequest
                                                    koiFarmRequest){
        KoiFarmResponse updatedKoiFarm = iKoiFarmsService.updateKoiFarmRes(koiFarmRequest,id);
        return ResponseEntity.ok(updatedKoiFarm);
    }

//    @PutMapping("/{farmId}/koi/{koiId}")
//    public ResponseEntity<KoiOfFarm> updateKoiQuantity(
//            @PathVariable Long farmId,
//            @PathVariable Long koiId) {
//        KoiOfFarm updatedKoiOfFarm = koiOfFarmService.updateKoiQuantity(farmId, koiId);
//        return ResponseEntity.ok(updatedKoiOfFarm);
//    }

    @DeleteMapping("/deleteFarm/{id}")
    public ResponseEntity<?> deleteKoiFarm(@PathVariable Long id){
        KoiFarmResponse koiFarms = iKoiFarmsService.deleteKoiFarmRes(id);
        return new ResponseEntity<>(koiFarms, HttpStatus.OK);
    }
}
