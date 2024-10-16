package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.DeliveryHistoryRequest;
import fall24.swp391.KoiOrderingSystem.model.response.DeliveryHistoryResponse;
import fall24.swp391.KoiOrderingSystem.pojo.DeliveryHistory;

import java.util.List;

public interface IDeliveryHistoryService {

    DeliveryHistoryResponse addDeliveryHistory(DeliveryHistoryRequest deliveryHistoryRequest, Long bookingId) throws Exception;

    DeliveryHistoryResponse updateDeliveryHistory(Long deliveryHistoryId,DeliveryHistoryRequest deliveryHistoryRequest) throws Exception;

    void deleteDeliveryHistory(Long deliveryHistoryId);

     List<DeliveryHistoryResponse> getDeliveryHistory(Long bookingId);
}
