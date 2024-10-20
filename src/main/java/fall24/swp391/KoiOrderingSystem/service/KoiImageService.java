package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.pojo.KoiImage;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import fall24.swp391.KoiOrderingSystem.repo.IKoiImageRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KoiImageService implements IKoiImageService {
    @Autowired
    private IKoiImageRepository koiImageRepository;

    @Autowired
    private IKoisRepository koisRepository;
    @Override
    public List<KoiImage> getKoiImage(Long koiId) {
        Kois koi = koisRepository.findKoisById(koiId);
        return koiImageRepository.findKoiImageByKois(koi);
    }

    @Override
    public KoiImage createKoiImage(Long koiId, String imageUrl) {
        KoiImage koiImage = new KoiImage();
        koiImage.setImageUrl(imageUrl);
        if(koisRepository.findKoisById(koiId) == null) {
            throw new NotFoundEntity("Koi does not exist");
        }
        koiImage.setKois(koisRepository.findKoisById(koiId));
        return koiImageRepository.save(koiImage);
    }

    @Override
    public void deleteKoiImage(Long id) {
        koiImageRepository.deleteById(id);
    }
}
