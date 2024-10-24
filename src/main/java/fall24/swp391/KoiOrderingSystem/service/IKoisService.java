package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.KoiRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;

import java.util.List;

public interface IKoisService {
    List<KoiResponse> findAll();

    KoiResponse createKois(KoiRequest koiRequest);

    KoiResponse updateKoi(Long Id,KoiRequest koiRequest);

    KoiResponse deletebyId(Long Id);

    KoiResponse getKoiById (Long Id);

    List<KoiResponse> findAllActive();
}
