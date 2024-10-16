package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.model.request.DeliveryHistoryRequest;
import fall24.swp391.KoiOrderingSystem.model.request.DeliveryRequest;
import fall24.swp391.KoiOrderingSystem.model.response.DeliveryHistoryResponse;
import fall24.swp391.KoiOrderingSystem.model.response.DeliveryResponse;
import fall24.swp391.KoiOrderingSystem.service.IDeliveryHistoryService;
import fall24.swp391.KoiOrderingSystem.service.IDeliveryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/")
@SecurityRequirement(name = "api")
//@PreAuthorize("hasAuthority('DELIVERING_STAFF')")
public class DeliveryController {
    @Autowired
    IDeliveryService deliveryService;

    @Autowired
    IDeliveryHistoryService deliveryHistoryService;
    @PostMapping("delivery-history/{bookingId}")
    public ResponseEntity<?> addDeliveryHistory(@Validated @RequestBody DeliveryHistoryRequest deliveryHistoryRequest,@PathVariable Long bookingId) throws Exception {
        DeliveryHistoryResponse deliveryHistory = deliveryHistoryService.addDeliveryHistory(deliveryHistoryRequest,bookingId);
        return ResponseEntity.ok(deliveryHistory);
    }

    @PostMapping("delivery/{bookingId}")
    public ResponseEntity<?> addDelivery(@Validated @RequestBody DeliveryRequest deliveryRequest,@PathVariable Long bookingId) throws Exception {
        DeliveryResponse delivery= deliveryService.addDelivery(deliveryRequest,bookingId);
        return ResponseEntity.ok(delivery);
    }

    @PutMapping("delivery-history/{deliveryHistoryId}")
    public ResponseEntity<?> updateDeliveryHistory(@PathVariable Long deliveryHistoryId,@RequestBody DeliveryHistoryRequest deliveryHistoryRequest) throws Exception {
        DeliveryHistoryResponse deliveryHistory = deliveryHistoryService.updateDeliveryHistory(deliveryHistoryId,deliveryHistoryRequest);
        return ResponseEntity.ok(deliveryHistory);
    }

    @PutMapping("delivery/{deliveryId}")
    public ResponseEntity<?> updateDelivery(@PathVariable Long deliveryId,@RequestBody DeliveryRequest deliveryRequest) throws Exception {
        DeliveryResponse delivery = deliveryService.updateDeliveryHistory(deliveryId,deliveryRequest);
        return ResponseEntity.ok(delivery);
    }

    @DeleteMapping("delivery-history/{deliveryHistoryId}")
    public ResponseEntity<?> deleteDeliveryHistory(@PathVariable Long deliveryHistoryId) throws Exception {
        deliveryHistoryService.deleteDeliveryHistory(deliveryHistoryId);
        return ResponseEntity.ok("Delete successful");
    }

    @DeleteMapping("delivery/{deliveryId}")
    public ResponseEntity<?> deleteDelivery(@PathVariable Long deliveryId) throws Exception {
        deliveryService.deleteDelivery(deliveryId);
        return ResponseEntity.ok("Delete successful");
    }

    @GetMapping("delivery-history/{bookingId}")
    public ResponseEntity<?> getDeliveryHistory(@PathVariable Long bookingId) throws Exception {
        List<DeliveryHistoryResponse> list = deliveryHistoryService.getDeliveryHistory(bookingId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("delivery")
    public ResponseEntity<?> getAllDelivery() throws Exception {
        List<DeliveryResponse> list = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(list);
    }

    @GetMapping("delivery/{deliveryId}")
    public ResponseEntity<?> getDeliveryById(@PathVariable Long deliveryId ) throws Exception {
        DeliveryResponse deliveryResponse = deliveryService.getDelivery(deliveryId);
        return ResponseEntity.ok(deliveryResponse);
    }
}
