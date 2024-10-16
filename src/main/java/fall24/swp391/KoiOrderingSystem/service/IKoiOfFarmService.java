package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.KoiOfFarmRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiOfFarmResponse;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;

import java.util.List;

public interface IKoiOfFarmService {
    List<KoiOfFarmResponse> findKoiOfFarmByKoiId(Long koiId);

    List<KoiOfFarmResponse> findKoiOfFarmByFarmId(Long farmId);

    KoiOfFarm createKoiOfFarm(KoiOfFarmRequest koiOfFarmRequest);

    void deleteById(Long Id);

    KoiOfFarm updateKoiOfFarm(Long Id,KoiOfFarmRequest koiOfFarmRequest);

}
