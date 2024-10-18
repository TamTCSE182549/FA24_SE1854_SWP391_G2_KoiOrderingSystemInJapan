package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.KoiFarmImageRequest;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmImage;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmImageRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoiFarmImageService implements IKoiFarmImageService{
    @Autowired
    private IKoiFarmImageRepository koiFarmImageRepository;

    @Autowired
    private IKoiFarmsRepository iKoiFarmsRepository;

    @Autowired
    private ModelMapper modelMapper;

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
    public KoiFarmImage createKoiFarmImage(KoiFarmImageRequest koiFarmImageRequest) {
        KoiFarms koiFarms = iKoiFarmsRepository.findKoiFarmsById(koiFarmImageRequest.getFarmId());
        if(koiFarms == null){
            throw new EntityNotFoundException("KoiOfFarm not found");
        }
        KoiFarmImage koiFarmImage = new KoiFarmImage();
        koiFarmImage.setKoiFarms(koiFarms);
        koiFarmImage.setImageUrl(koiFarmImageRequest.getImageUrl());
        koiFarmImageRepository.save(koiFarmImage);
        return koiFarmImage;
    }
}
