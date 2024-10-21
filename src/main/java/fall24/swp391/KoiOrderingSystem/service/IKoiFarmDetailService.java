package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.KoiFarmDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiFarmDetailResponse;

import java.util.List;

public interface IKoiFarmDetailService {
    List<KoiFarmDetailResponse> farmDetailByFarm(Long farmID);

    KoiFarmDetailResponse createFarmDetailRes(KoiFarmDetailRequest koiFarmDetailRequest);

    void deleteFarmDetail(Long farmDetailID);

    KoiFarmDetailResponse updateKoiFarmDetailRes(Long farmDetailID, KoiFarmDetailRequest koiFarmDetailRequest);
}
