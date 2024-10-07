package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.DeliveryHistoryRequest;
import fall24.swp391.KoiOrderingSystem.model.request.DeliveryRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Deliveries;

import java.util.List;

public interface IDeliveryService {
    Deliveries addDelivery(DeliveryRequest deliveryRequest, Long bookingId);

    Deliveries updateDeliveryHistory(Long deliveryId, DeliveryRequest deliveryRequest) throws Exception;

    void deleteDelivery(Long deliveryId);

    List<Deliveries> getAllDeliveries();

}
