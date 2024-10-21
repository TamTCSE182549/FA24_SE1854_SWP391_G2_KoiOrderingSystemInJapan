package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.KoiImage;

import java.util.List;

public interface IKoiImageService {
    List<KoiImage> getKoiImage(Long koiId);
    KoiImage createKoiImage(Long koiId,String imageUrl);
    void deleteKoiImage(Long id);
}
