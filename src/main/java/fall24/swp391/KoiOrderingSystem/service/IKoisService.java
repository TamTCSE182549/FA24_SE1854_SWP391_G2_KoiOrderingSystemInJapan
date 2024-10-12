package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.KoiRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;

import java.util.List;

public interface IKoisService {
    List<Kois> findAll();

    List<Kois> getKoiByFarmId(Long farmId);

    Kois createKois(KoiRequest koiRequest, Long farmId);

    Kois updateKoi(Long Id,KoiRequest koiRequest);

    void deletebyId(Long Id);
}
