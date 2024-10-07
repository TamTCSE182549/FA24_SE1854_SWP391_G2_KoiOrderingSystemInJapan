package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;

import java.util.List;

public interface IKoiFarmsService {
    List<KoiFarms> findAll();

    KoiFarms findById(Long Id);

    KoiFarms save(KoiFarms koiFarms);

    void deletebyId(Long Id);
}
