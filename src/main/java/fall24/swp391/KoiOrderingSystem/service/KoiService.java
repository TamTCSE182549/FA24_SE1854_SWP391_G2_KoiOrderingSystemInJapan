package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.NotCreateException;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.request.KoiRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiResponse;
import fall24.swp391.KoiOrderingSystem.pojo.*;
import fall24.swp391.KoiOrderingSystem.repo.ICategoriesRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoisRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoiService implements IKoisService{

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private IKoisRepository iKoisRepository;

    @Autowired
    private IKoiFarmsRepository iKoiFarmsRepository;

    @Autowired
    private ICategoriesRepository iCategoriesRepository;
    @Override
    public List<Kois> findAll() {
        return iKoisRepository.findAll();
    }

    @Override
    public KoiResponse getKoiById(Long Id) {
            Kois kois =iKoisRepository.findById(Id)
                    .orElseThrow(() -> new NotFoundEntity("Not found Kois"));
            KoiResponse koiResponse =modelMapper.map(kois,KoiResponse.class);
            return koiResponse;
    }

    @Override
    public KoiResponse createKois(KoiRequest koiRequest) {
        try {
            Kois kois = modelMapper.map(koiRequest, Kois.class);
            kois.setId(null);
            if (koiRequest.getCategoryId() != null) {
                Categories categories = iCategoriesRepository.findById(koiRequest.getCategoryId()).get();
                kois.setCategory(categories);
            }
            Kois createKoi = iKoisRepository.save(kois);
            KoiResponse koiResponse =modelMapper.map(createKoi,KoiResponse.class);
            return koiResponse;
        }catch (Exception e){
            throw new NotCreateException(e.getMessage());
        }
    }

    @Override
    public KoiResponse updateKoi(Long Id, KoiRequest koiRequest) {
        Kois kois = iKoisRepository.findById(Id)
                .orElseThrow(() -> new NotFoundEntity("Not Found Kois"));
        if (koiRequest.getCategoryId() != null) {
            Categories categories = iCategoriesRepository.findById(koiRequest.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            kois.setCategory(categories);
        }
        kois.setKoiName(koiRequest.getKoiName());
        kois.setOrigin(koiRequest.getOrigin());
        kois.setColor(koiRequest.getColor());
        kois.setDescription(koiRequest.getDescription());
        kois.setKoiImage(koiRequest.getKoiImage());
        kois.setActive(koiRequest.isActive());

        Kois updatedKoi = iKoisRepository.save(kois);


        KoiResponse koiResponse = modelMapper.map(updatedKoi, KoiResponse.class);
        return koiResponse;
    }


    @Override
    public void deletebyId(Long Id) {
        Kois kois = iKoisRepository.findById(Id)
                .orElseThrow(() -> new NotFoundEntity("Not Found Kois"));
//        bookingKoiDetailRepository.deleteByKoiId(id);

        iKoisRepository.delete(kois);
    }
}
