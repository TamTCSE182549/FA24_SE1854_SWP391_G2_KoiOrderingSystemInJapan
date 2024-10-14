package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.KoiOfFarmRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiOfFarmResponse;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoiOfFarmRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoisRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoiOfFarmService implements IKoiOfFarmService{
    @Autowired
    private IKoiOfFarmRepository koiOfFarmRepository;

    @Autowired
    private IKoisRepository iKoisRepository;

    @Autowired
    private IKoiFarmsRepository iKoiFarmsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public void koiOfFarmService(IKoiOfFarmRepository koiOfFarmRepo){
        koiOfFarmRepository = koiOfFarmRepo;
    }

    @Override
    public List<KoiOfFarm> findAll() {
        return koiOfFarmRepository.findAll();
    }

    @Override
    public KoiOfFarm findById(Long Id) {
        return null;
    }

    @Override
    public KoiOfFarm addKoiToFarm(KoiOfFarmRequest koiOfFarmRequest) {
        KoiFarms koiFarms = iKoiFarmsRepository.findKoiFarmsById(koiOfFarmRequest.getFarmId());
        if(koiFarms == null){
            throw new EntityNotFoundException("KoiOfFarm not found");
        }
        Kois kois = iKoisRepository.findKoisById(koiOfFarmRequest.getKoiId());
        if(kois == null){
            throw new EntityNotFoundException("Koi not found");
        }
        KoiOfFarm koiOfFarm = new KoiOfFarm();
        koiOfFarm.setKoiFarms(koiFarms);
        koiOfFarm.setKois(kois);
        koiOfFarm.setAvailable(true);
        koiOfFarm.setQuantity(koiOfFarmRequest.getQuantity());
        koiOfFarmRepository.save(koiOfFarm);
        return koiOfFarm;
    }

    @Override
    public void deleteById(Long Id) {
        koiOfFarmRepository.deleteById(Id);
    }

    @Override
    public List<KoiOfFarmResponse> findKoiOfFarmByFarmId(Long farmId) {
        List<KoiOfFarm> koiOfFarmsList = koiOfFarmRepository.findByKoiFarms_Id(farmId);
        return koiOfFarmsList.stream().map(koiOfFarm -> {
            KoiOfFarmResponse koiOfFarmResponse = modelMapper.map(koiOfFarm, KoiOfFarmResponse.class);
            return koiOfFarmResponse;
        }).toList();
    }

//    @Override
//    public KoiOfFarm updateKoiQuantity(Long farmId, Long koiId, int newQuantity) {
//        KoiOfFarm koiOfFarm = koiOfFarmRepository.findByKoiFarms_IdAndKois_Id(farmId, koiId);
//        if (koiOfFarm == null) {
//            throw new EntityNotFoundException("KoiOfFarm not found for given farmId and koiId");
//        }
//        koiOfFarm.setQuantity(newQuantity);
//        return koiOfFarmRepository.save(koiOfFarm);
//    }
}
