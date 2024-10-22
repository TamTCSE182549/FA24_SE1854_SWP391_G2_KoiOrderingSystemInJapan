package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.model.request.TourRequest;
import fall24.swp391.KoiOrderingSystem.model.response.TourResponse;
import fall24.swp391.KoiOrderingSystem.service.ITourService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/tour")
@SecurityRequirement(name = "api")
public class TourController {

    @Autowired
    private ITourService iTourService;

    @GetMapping("/listTourResponseActive")
    public ResponseEntity<?> showAllTourResponse(){
        List<TourResponse> tours = iTourService.tourResponseListActive();
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/showAll")
    public ResponseEntity<?> showAll(){
        List<TourResponse> tours = iTourService.showAll();
        return ResponseEntity.ok(tours);
    }

    @GetMapping("/showAllPageable")
    public ResponseEntity<?> showAllPageable(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", iTourService.showAllPageable(page,size).getTotalPages());
        response.put("pageNumber", iTourService.showAllPageable(page,size).getNumber());
        response.put("content", iTourService.showAllPageable(page, size).get());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/showTourByName/{tourName}")
    public ResponseEntity<?> showTourByName(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @PathVariable String tourName){
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", iTourService.showTourByName(page,size,tourName).getTotalPages());
        response.put("currentPage", iTourService.showTourByName(page,size,tourName).getNumber());
        response.put("content", iTourService.showTourByName(page, size,tourName).get());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createTourRes")
    public ResponseEntity<?> createTourRes(@RequestBody TourRequest tourRequest) {
        TourResponse tourResponse = iTourService.createTourRes(tourRequest);
        return new ResponseEntity<>(tourResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteTourRes/{id}")
    public ResponseEntity<?> deleteTourRes(@PathVariable @NotNull Long id) {
        TourResponse tourResponse = iTourService.deleteTourRes(id);
        return new ResponseEntity<>(tourResponse, HttpStatus.OK);
    }

    @PutMapping("/updateTourRes/{id}")
    public ResponseEntity<?> updateTourRes(@PathVariable @NotNull Long id, @RequestBody TourRequest tourRequest) {
        TourResponse tourResponse = iTourService.updateTourRes(id, tourRequest);
        return ResponseEntity.ok(tourResponse);
    }

    @GetMapping("/findById/{tourId}")
    public ResponseEntity<?> findById(@PathVariable @NotNull Long tourId){
        TourResponse tourResponse = iTourService.findById(tourId);
        return ResponseEntity.ok(tourResponse);
    }

    @GetMapping("/findTourByKoiName/{koiName}")
    public ResponseEntity<?> showTourByKoiName(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @PathVariable String koiName){
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", iTourService.findTourByKoiName(page,size,koiName).getTotalPages());
        response.put("currentPage", iTourService.findTourByKoiName(page,size,koiName).getNumber());
        response.put("content", iTourService.findTourByKoiName(page, size,koiName).get());
        return ResponseEntity.ok(response);
    }
}
