package fall24.swp391.KoiOrderingSystem.controller;


import fall24.swp391.KoiOrderingSystem.model.request.KoiFarmRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiFarmResponse;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.service.IKoiFarmsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/koi-farm")
@SecurityRequirement(name = "api")
public class KoiFarmController {

    @Autowired
    private IKoiFarmsService iKoiFarmsService;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateKoiFarms(@PathVariable Long id,
                                            @RequestBody KoiFarmRequest
                                                    koiFarmRequest){
        KoiFarmResponse updatedKoiFarm = iKoiFarmsService.updateKoiFarmRes(koiFarmRequest,id);
        return ResponseEntity.ok(updatedKoiFarm);
    }

    @DeleteMapping("/deleteFarm/{id}")
    public ResponseEntity<?> deleteKoiFarm(@PathVariable Long id){
        KoiFarmResponse koiFarms = iKoiFarmsService.deleteKoiFarmRes(id);
        return new ResponseEntity<>(koiFarms, HttpStatus.OK);
    }
}
