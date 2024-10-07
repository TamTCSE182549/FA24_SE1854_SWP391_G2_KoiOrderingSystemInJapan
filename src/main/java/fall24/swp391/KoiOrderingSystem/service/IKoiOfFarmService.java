package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;

import java.util.List;

public interface IKoiOfFarmService {
    List<KoiOfFarm> findAll();

    KoiOfFarm findById(Long Id);

    KoiOfFarm save(KoiOfFarm koiOfFarm);

    void deleteById(Long Id);
}
