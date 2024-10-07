package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.DeliveryHistoryRequest;
import fall24.swp391.KoiOrderingSystem.pojo.DeliveryHistory;

import java.util.List;

public interface IDeliveryHistoryService {

    DeliveryHistory addDeliveryHistory(DeliveryHistoryRequest deliveryHistoryRequest, Long bookingId) throws Exception;

    DeliveryHistory updateDeliveryHistory(Long deliveryHistoryId,DeliveryHistoryRequest deliveryHistoryRequest) throws Exception;

    void deleteDeliveryHistory(Long deliveryHistoryId);

     List<DeliveryHistory> getDeliveryHistory(Long bookingId);
}
