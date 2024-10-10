package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.model.request.DeliveryHistoryRequest;
import fall24.swp391.KoiOrderingSystem.model.request.DeliveryRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Deliveries;
import fall24.swp391.KoiOrderingSystem.pojo.DeliveryHistory;
import fall24.swp391.KoiOrderingSystem.service.IDeliveryHistoryService;
import fall24.swp391.KoiOrderingSystem.service.IDeliveryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/")
@SecurityRequirement(name = "api")
@PreAuthorize("hasAuthority('DELIVERING_STAFF')")
public class DeliveryController {
    @Autowired
    IDeliveryService deliveryService;

    @Autowired
    IDeliveryHistoryService deliveryHistoryService;
    @PostMapping("delivery-history/{bookingId}")
    public ResponseEntity<?> addDeliveryHistory(@Validated @RequestBody DeliveryHistoryRequest deliveryHistoryRequest,@PathVariable Long bookingId) throws Exception {
        DeliveryHistory deliveryHistory = deliveryHistoryService.addDeliveryHistory(deliveryHistoryRequest,bookingId);
        return ResponseEntity.ok(deliveryHistory);
    }

    @PostMapping("delivery/{bookingId}")
    public ResponseEntity<?> addDelivery(@Validated @RequestBody DeliveryRequest deliveryRequest,@PathVariable Long bookingId) throws Exception {
        Deliveries delivery= deliveryService.addDelivery(deliveryRequest,bookingId);
        return ResponseEntity.ok(delivery);
    }

    @PutMapping("delivery-history/{bookingId}")
    public ResponseEntity<?> updateDeliveryHistory(@PathVariable Long bookingId,@RequestBody DeliveryHistoryRequest deliveryHistoryRequest) throws Exception {
        DeliveryHistory deliveryHistory = deliveryHistoryService.updateDeliveryHistory(bookingId,deliveryHistoryRequest);
        return ResponseEntity.ok(deliveryHistory);
    }

    @PutMapping("delivery/{bookingId}")
    public ResponseEntity<?> updateDelivery(@PathVariable Long bookingId,@RequestBody DeliveryRequest deliveryRequest) throws Exception {
        Deliveries delivery = deliveryService.updateDeliveryHistory(bookingId,deliveryRequest);
        return ResponseEntity.ok(delivery);
    }

    @DeleteMapping("delivery-history/{deliveryHistoryId}")
    public ResponseEntity<?> deleteDeliveryHistory(@PathVariable Long deliveryHistoryId) throws Exception {
        deliveryService.deleteDelivery(deliveryHistoryId);
        return ResponseEntity.ok("Delete successful");
    }

    @DeleteMapping("delivery/{deliveryId}")
    public ResponseEntity<?> deleteDelivery(@PathVariable Long deliveryId) throws Exception {
        deliveryService.deleteDelivery(deliveryId);
        return ResponseEntity.ok("Delete successful");
    }

    @GetMapping("delivery-history/{bookingId}")
    public ResponseEntity<?> getDeliveryHistory(@PathVariable Long bookingId) throws Exception {
        List<DeliveryHistory> list = deliveryHistoryService.getDeliveryHistory(bookingId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("delivery")
    public ResponseEntity<?> getAllDelivery() throws Exception {
        List<Deliveries> list = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(list);
    }
}
