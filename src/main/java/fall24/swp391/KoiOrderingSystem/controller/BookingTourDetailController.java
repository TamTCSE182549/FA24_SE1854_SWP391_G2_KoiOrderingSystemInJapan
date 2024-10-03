package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.service.IBookingTourDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BookingTourDetailController {

    @Autowired
    private IBookingTourDetailService iBookingTourDetailService;


}
