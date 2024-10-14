package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.KoiOfFarmRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiOfFarmResponse;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;

import java.util.List;

public interface IKoiOfFarmService {
    List<KoiOfFarm> findAll();

    KoiOfFarm findById(Long Id);

    KoiOfFarm addKoiToFarm(KoiOfFarmRequest koiOfFarmRequest);

    void deleteById(Long Id);

    List<KoiOfFarmResponse> findKoiOfFarmByFarmId(Long farmId);

//    KoiOfFarm updateKoiQuantity(Long farmId, Long koiId, int newQuantity);
}
