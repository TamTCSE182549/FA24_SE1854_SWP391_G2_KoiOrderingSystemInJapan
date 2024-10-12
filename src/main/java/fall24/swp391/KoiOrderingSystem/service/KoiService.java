package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.request.KoiRequest;
import fall24.swp391.KoiOrderingSystem.pojo.*;
import fall24.swp391.KoiOrderingSystem.repo.ICategoriesRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoisRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<Kois> getKoiByFarmId(Long farmId) {
            KoiFarms koiFarms =iKoiFarmsRepository.findById(farmId)
                    .orElseThrow(() -> new NotFoundEntity("Not found Farm"));
            return iKoisRepository.findByKoiOfFarmList_KoiFarms_Id(farmId) ;
    }

    @Override
    public Kois createKois(KoiRequest koiRequest, Long farmId) {
        KoiFarms koiFarms =iKoiFarmsRepository.findById(farmId)
                .orElseThrow(() -> new NotFoundEntity("Not found Farm"));

        Kois kois = modelMapper.map(koiRequest, Kois.class);
        kois.setId(null);
        if(koiRequest.getCategoryId()!=null){
            Categories categories = iCategoriesRepository.findById(koiRequest.getCategoryId()).get();
            kois.setCategory(categories);
        }

        Kois createdKoi= iKoisRepository.save(kois);

        KoiOfFarm koiOfFarm = new KoiOfFarm();
        koiOfFarm.setKois(createdKoi);
        koiOfFarm.setKoiFarms(koiFarms);
        koiOfFarm.setQuantity(koiRequest.getQuantity());
        koiOfFarm.setAvailable(koiRequest.isAvailable());


        iKoiFarmsRepository.save(koiFarms);
        return createdKoi;
    }

    @Override
    public Kois updateKoi(Long Id, KoiRequest koiRequest) {
        return null;
    }

    @Override
    public void deletebyId(Long Id) {

    }
}
