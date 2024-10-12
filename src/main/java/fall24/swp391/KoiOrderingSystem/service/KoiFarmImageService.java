package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmImage;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmImageRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoiFarmImageService implements IKoiFarmImageService{
    private IKoiFarmImageRepository koiFarmImageRepository;

    public void koiFarmImageService(IKoiFarmImageRepository koiFarmImageRepo){
        koiFarmImageRepository = koiFarmImageRepo;
    }

    @Override
    public List<KoiFarmImage> findAll() {
        return koiFarmImageRepository.findAll();
    }

    @Override
    public KoiFarmImage findById(Long Id) {
        return null;
    }

    @Override
    public KoiFarmImage save(KoiFarmImage koiFarmImage) {
        return koiFarmImageRepository.save(koiFarmImage);
    }

    @Override
    public void deleteById(Long Id) {
        koiFarmImageRepository.deleteById(Id);
    }
}
