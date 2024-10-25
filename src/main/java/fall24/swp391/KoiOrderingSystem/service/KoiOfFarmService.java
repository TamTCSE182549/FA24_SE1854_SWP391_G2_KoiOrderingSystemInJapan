package fall24.swp391.KoiOrderingSystem.service;


import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.request.KoiOfFarmRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiOfFarmResponse;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoiOfFarmRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoisRepository;
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

    @Override
    public List<KoiOfFarmResponse> findKoiOfFarmByKoiId(Long koiId) {
        List<KoiOfFarm> koiOfFarmsList = koiOfFarmRepository.findByKoisId(koiId);
        return koiOfFarmsList.stream().map(koiOfFarm -> {
            KoiOfFarmResponse koiOfFarmResponse = modelMapper.map(koiOfFarm, KoiOfFarmResponse.class);
            return koiOfFarmResponse;
        }).toList();
    }

    @Override
    public List<KoiOfFarmResponse> findKoiOfFarmByFarmId(Long farmId) {
        KoiFarms koiFarms = iKoiFarmsRepository.findKoiFarmsById(farmId);
        if(koiFarms == null){
            throw new NotFoundEntity("Koi Farm Not Found");
        }
        List<KoiOfFarm> koiOfFarmList = koiOfFarmRepository.findKoiOfFarmBykoiFarms(koiFarms);
        return koiOfFarmList.stream().map(koiOfFarm -> {
            KoiOfFarmResponse koiOfFarmResponse = modelMapper.map(koiOfFarm, KoiOfFarmResponse.class);
            koiOfFarmResponse.setKoi_id(koiOfFarm.getKois().getId());
            koiOfFarmResponse.setFarm_id(farmId);
            koiOfFarmResponse.setKoiName(koiOfFarm.getKois().getKoiName());
            return koiOfFarmResponse;
        }).toList();
    }
//
//    @Override
//    public List<KoiOfFarmResponse> findKoiOfFarmByFarmId(Long farmId) {
//        List<KoiOfFarm> koiOfFarmsList = koiOfFarmRepository.findByKoiFarms_Id(farmId);
//        return koiOfFarmsList.stream().map(koiOfFarm -> {
//            KoiOfFarmResponse koiOfFarmResponse = modelMapper.map(koiOfFarm, KoiOfFarmResponse.class);
//            return koiOfFarmResponse;
//        }).toList();
//    }

    @Override

    public KoiOfFarm createKoiOfFarm(KoiOfFarmRequest koiOfFarmRequest) {
        try {
            KoiOfFarm koiOfFarms = koiOfFarmRepository.findByFarmIdAndKoiId(koiOfFarmRequest.getFarmId(), koiOfFarmRequest.getKoiId());
            if(koiOfFarms == null ){
                koiOfFarms = new KoiOfFarm();
            } else {
                koiOfFarms.setQuantity(koiOfFarmRequest.getQuantity()+koiOfFarms.getQuantity());
                return koiOfFarmRepository.save(koiOfFarms);
            }
            koiOfFarms.setQuantity(koiOfFarmRequest.getQuantity());
            koiOfFarms.setAvailable(koiOfFarmRequest.isAvailable());
            Kois kois = iKoisRepository.findById(koiOfFarmRequest.getKoiId())
                    .orElseThrow(() -> new NotFoundEntity("NOT FOUND KOI ID"));
            if(!kois.isActive()){
                throw new NotFoundEntity("Koi inactivated");
            }
            koiOfFarms.setKois(kois);
            KoiFarms koiFarms = iKoiFarmsRepository.findById(koiOfFarmRequest.getFarmId())
                    .orElseThrow(() -> new NotFoundEntity("Not found Farm Id"));
            koiOfFarms.setKoiFarms(koiFarms);
            if(!koiFarms.isActive()){
                throw new NotFoundEntity("Koi Farm inactivated");
            }
            return koiOfFarmRepository.save(koiOfFarms);
        }catch (Exception e){
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long Id) {
        KoiOfFarm koiOfFarm =koiOfFarmRepository.findById(Id)
                .orElseThrow(() -> new NotFoundEntity("Not Found"));
        koiOfFarmRepository.deleteById(Id);
    }

    @Override
    public KoiOfFarm updateKoiOfFarm(Long Id,KoiOfFarmRequest koiOfFarmRequest) {
        KoiOfFarm koiOfFarm = koiOfFarmRepository.findById(Id)
                .orElseThrow(() -> new NotFoundEntity("Not Found"));
        if (koiOfFarmRequest.getFarmId() != null) {
            KoiFarms koiFarms = iKoiFarmsRepository.findById(koiOfFarmRequest.getFarmId())
                    .orElseThrow(() -> new NotFoundEntity("Not found Farm Id"));
            koiOfFarm.setKoiFarms(koiFarms);
        }
        if(  koiOfFarmRequest.getKoiId() != null){
            Kois kois = iKoisRepository.findById(koiOfFarmRequest.getKoiId())
                    .orElseThrow(() -> new NotFoundEntity("Not Found Koi Id"));
            koiOfFarm.setKois(kois);
        }
        koiOfFarm.setAvailable(koiOfFarmRequest.isAvailable());
        koiOfFarm.setQuantity(koiOfFarmRequest.getQuantity());
        return koiOfFarmRepository.save(koiOfFarm);
    }

}
