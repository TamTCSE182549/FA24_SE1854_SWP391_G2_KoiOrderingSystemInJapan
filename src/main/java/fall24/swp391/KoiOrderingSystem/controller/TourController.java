package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import fall24.swp391.KoiOrderingSystem.service.ITourService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tour")
public class TourController {

    @Autowired
    private ITourService iTourService;

    @GetMapping("/list")
    public ResponseEntity<?> showAllTour(){
        List<Tours> tours = iTourService.tourList();
        if (tours.isEmpty()) {
            return ResponseEntity.ok("Empty list");
        } else {
            return ResponseEntity.ok(tours);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTour(@RequestBody Tours tours) {
        iTourService.createTour(tours);
        return new ResponseEntity<>("Create tour: "+ tours.getTourName() +" Success",HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTour(@PathVariable @NotNull Long id) {
        Tours tours = iTourService.deleteTourById(id);
        return new ResponseEntity<>(tours, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTour(@PathVariable @NotNull Long id, @RequestBody Tours tours) {
        iTourService.updateTour(id, tours);
        return ResponseEntity.ok(tours);
    }
}
