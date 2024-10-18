package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.KoiFarmRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiFarmResponse;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IKoiFarmsService {

    KoiFarmResponse createKoiFarm(KoiFarmRequest koiFarmRequest);

    List<KoiFarms> listKoiFarm();

    List<KoiFarmResponse> getFarmById(Long id);

    KoiFarms updateKoiFarm(Long id, KoiFarms koiFarm);

    KoiFarmResponse updateKoiFarmRes(KoiFarmRequest koiFarmRequest, Long id);

    KoiFarms deleteKoiFarm(Long id);

    KoiFarmResponse deleteKoiFarmRes(Long id);

    List<KoiFarmResponse> getAllKoiFarms();

    KoiFarmResponse convertToKoiFarmResponse(KoiFarms koiFarm);

    List<KoiFarmResponse> getFarmIsActive();

    Page<KoiFarmResponse> showFarmByName(int page, int size, String farmName);
}
