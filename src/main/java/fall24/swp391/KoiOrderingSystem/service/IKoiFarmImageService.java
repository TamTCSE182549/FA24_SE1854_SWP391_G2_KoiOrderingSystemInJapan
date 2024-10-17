package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.KoiFarmImageRequest;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmImage;

import java.util.List;

public interface IKoiFarmImageService {
    List<KoiFarmImage> findAll();

    KoiFarmImage findById(Long Id);

    KoiFarmImage createKoiFarmImage(KoiFarmImageRequest koiFarmImageRequest);
}
