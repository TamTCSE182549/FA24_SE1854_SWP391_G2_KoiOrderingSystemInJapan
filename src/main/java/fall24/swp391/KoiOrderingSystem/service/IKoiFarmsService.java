package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;

import java.util.List;

public interface IKoiFarmsService {

    KoiFarms createKoiFarm(KoiFarms koiFarm);

    List<KoiFarms> listKoiFarm();

    KoiFarms updateKoiFarm(Long id, KoiFarms koiFarm);

    KoiFarms deleteKoiFarm(Long id);
}
