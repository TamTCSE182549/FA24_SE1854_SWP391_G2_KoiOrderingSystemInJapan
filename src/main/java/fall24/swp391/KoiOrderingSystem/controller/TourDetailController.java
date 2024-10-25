package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.model.request.TourDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.TourDetailResponse;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import fall24.swp391.KoiOrderingSystem.service.ITourDetailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/TourDetail")
@SecurityRequirement(name = "api")
public class TourDetailController {

    @Autowired
    private ITourDetailService iTourDetailService;

    @Autowired
    private IKoiFarmsRepository iKoiFarmsRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createTourDetail(@RequestBody TourDetailRequest tourDetailRequest){
        TourDetailResponse tourDetailResponse = iTourDetailService.CREATE_TOUR_DETAIL_RESPONSES(tourDetailRequest);
        return ResponseEntity.ok(tourDetailResponse);
    }

    @GetMapping("/tour/{tourID}")
    public ResponseEntity<?> viewTourDetailByTourID(@PathVariable Long tourID){
        List<TourDetailResponse> tourDetailResponses = iTourDetailService.TOUR_DETAIL_RESPONSES_BY_TOUR(tourID);
        return ResponseEntity.ok(tourDetailResponses);
    }

    @GetMapping("/farm/{farmID}")
    public ResponseEntity<?> viewTourDetailByFarmID(@PathVariable Long farmID){
        List<TourDetailResponse> tourDetailResponses = iTourDetailService.TOUR_RESPONSES_BY_FARM(farmID);
        return ResponseEntity.ok(tourDetailResponses);
    }

    @DeleteMapping("/{tourDetailID}")
    public ResponseEntity<?> deleteTourDetail(@PathVariable Long tourDetailID){
        iTourDetailService.DELETE_TOUR_DETAIL(tourDetailID);
        return ResponseEntity.ok("Delete Tour Detail complete");
    }
}
