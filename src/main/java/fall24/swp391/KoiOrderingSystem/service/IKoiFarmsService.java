package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.KoiFarmRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiFarmResponse;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;

import java.util.List;

public interface IKoiFarmsService {

    KoiFarms createKoiFarm(KoiFarms koiFarm);

    KoiFarmResponse createKoiFarmRes(KoiFarmRequest koiFarmRequest);

    List<KoiFarms> listKoiFarm();

    KoiFarms updateKoiFarm(Long id, KoiFarms koiFarm);

    KoiFarms deleteKoiFarm(Long id);
}
