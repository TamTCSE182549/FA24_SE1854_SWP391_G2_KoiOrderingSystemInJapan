package fall24.swp391.KoiOrderingSystem.controller;


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
    public ResponseEntity<KoiFarms> createKoiFarm(@RequestBody KoiFarms koiFarms){
        KoiFarms createdKoiFarm = iKoiFarmsService.createKoiFarm(koiFarms);
        return  ResponseEntity.status(HttpStatus.CREATED).body(createdKoiFarm);
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

    @PutMapping("/update")
    public ResponseEntity<KoiFarms> updateKoiFarms(@PathVariable Long id,
                                                   @RequestBody KoiFarms koiFarms){
        try{
            KoiFarms updatedKoiFarm = iKoiFarmsService.updateKoiFarm(id,koiFarms);
            return new ResponseEntity<>(updatedKoiFarm, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<KoiFarms> deleteKoiFarm(@PathVariable Long id){
        KoiFarms koiFarms = iKoiFarmsService.deleteKoiFarm(id);
        return new ResponseEntity<>(koiFarms, HttpStatus.OK);
    }
}
