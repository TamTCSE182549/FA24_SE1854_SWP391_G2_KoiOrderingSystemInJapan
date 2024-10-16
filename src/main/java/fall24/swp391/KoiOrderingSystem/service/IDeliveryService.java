package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.DeliveryRequest;
import fall24.swp391.KoiOrderingSystem.model.response.DeliveryResponse;

import java.util.List;

public interface IDeliveryService {
    DeliveryResponse addDelivery(DeliveryRequest deliveryRequest, Long bookingId);

    DeliveryResponse updateDeliveryHistory(Long deliveryId, DeliveryRequest deliveryRequest) throws Exception;

    void deleteDelivery(Long deliveryId);

    DeliveryResponse getDelivery(Long deliveryId);

    List<DeliveryResponse> getAllDeliveries();

}
