package fall24.swp391.KoiOrderingSystem.controller;



import fall24.swp391.KoiOrderingSystem.model.request.KoiOfFarmRequest;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import fall24.swp391.KoiOrderingSystem.service.IKoiOfFarmService;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/KoiOfFarm")
@SecurityRequirement(name = "api")
public class KoiOfFarmController {

    @Autowired
    private IKoiOfFarmService iKoiOfFarmService;

    @PostMapping
    public ResponseEntity<?> createKoiOfFarm(@RequestBody KoiOfFarmRequest koiOfFarmRequest){
        KoiOfFarm koiOfFarm = iKoiOfFarmService.createKoiOfFarm(koiOfFarmRequest);
        return ResponseEntity.ok(koiOfFarm);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<?> updateKoiOfFarm(@PathVariable Long Id,@RequestBody KoiOfFarmRequest koiOfFarmRequest){
        KoiOfFarm koiOfFarm =iKoiOfFarmService.updateKoiOfFarm(Id,koiOfFarmRequest);
        return ResponseEntity.ok(koiOfFarm);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<KoiOfFarm> koiOfFarms =iKoiOfFarmService.getAll();
        return  ResponseEntity.ok(koiOfFarms);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<?> getById(@PathVariable Long Id){
        KoiOfFarm koiOfFarm = iKoiOfFarmService.getById(Id);
        return ResponseEntity.ok(koiOfFarm);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteById(@PathVariable Long Id){
       iKoiOfFarmService.deleteById(Id);
       return ResponseEntity.ok("Delete complete");
    }
}
