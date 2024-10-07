package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmImage;

import java.util.List;

public interface IKoiFarmImageService {
    List<KoiFarmImage> findAll();

    KoiFarmImage findById(Long Id);

    KoiFarmImage save(KoiFarmImage koiFarmImage);

    void deleteById(Long Id);
}
