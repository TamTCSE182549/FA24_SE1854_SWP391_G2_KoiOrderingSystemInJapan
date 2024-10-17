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

//    @GetMapping("/list")
//    public ResponseEntity<?> showAllTour(){
//        List<Tours> tours = iTourService.tourList();
//        if (tours.isEmpty()) {
//            return ResponseEntity.ok("Empty list");
//        } else {
//            return ResponseEntity.ok(tours);
//        }
//    }

    @GetMapping("/listTourResponseActive")
    public ResponseEntity<?> showAllTourResponse(){
        List<TourResponse> tours = iTourService.tourResponseListActive();
        if (tours.isEmpty()) {
            return ResponseEntity.ok("Empty list");
        } else {
            return ResponseEntity.ok(tours);
        }
    }

    @GetMapping("/showAll")
    public ResponseEntity<?> showAll(){
        List<TourResponse> tours = iTourService.showAll();
        if (tours.isEmpty()) {
            return ResponseEntity.ok("Empty list");
        } else {
            return ResponseEntity.ok(tours);
        }
    }

    @GetMapping("/showAllPageable")
    public ResponseEntity<?> showAllPageable(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size){
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", iTourService.showAllPageable(page,size).getTotalPages());
        response.put("pageNumber", iTourService.showAllPageable(page,size).getNumber());
        response.put("content", iTourService.showAllPageable(page, size).get());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/showTourByName/{tourName}")
    public ResponseEntity<?> showTourByName(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size, @PathVariable String tourName){
        Map<String, Object> response = new HashMap<>();
        response.put("totalPage", iTourService.showTourByName(page,size,tourName).getTotalPages());
        response.put("pageNumber", iTourService.showTourByName(page,size,tourName).getNumber());
        response.put("content", iTourService.showTourByName(page, size,tourName).get());
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/create")
//    public ResponseEntity<?> createTour(@RequestBody Tours tours) {
//        iTourService.createTour(tours);
//        return new ResponseEntity<>("Create tour: "+ tours.getTourName() +" Success",HttpStatus.CREATED);
//    }

    @PostMapping("/createTourRes")
    public ResponseEntity<?> createTourRes(@RequestBody TourRequest tourRequest) {
        TourResponse tourResponse = iTourService.createTourRes(tourRequest);
        return new ResponseEntity<>(tourResponse, HttpStatus.OK);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteTour(@PathVariable @NotNull Long id) {
//        Tours tours = iTourService.deleteTourById(id);
//        return new ResponseEntity<>(tours, HttpStatus.OK);
//    }

    @DeleteMapping("/deleteTourRes/{id}")
    public ResponseEntity<?> deleteTourRes(@PathVariable @NotNull Long id) {
        TourResponse tourResponse = iTourService.deleteTourRes(id);
        return new ResponseEntity<>(tourResponse, HttpStatus.OK);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateTour(@PathVariable @NotNull Long id, @RequestBody Tours tours) {
//        iTourService.updateTour(id, tours);
//        return ResponseEntity.ok(tours);
//    }

    @PutMapping("/updateTourRes/{id}")
    public ResponseEntity<?> updateTourRes(@PathVariable @NotNull Long id, @RequestBody TourRequest tourRequest) {
        TourResponse tourResponse = iTourService.updateTourRes(id, tourRequest);
        return ResponseEntity.ok(tourResponse);
    }
}
