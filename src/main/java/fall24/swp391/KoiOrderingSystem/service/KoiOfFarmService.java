package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.request.KoiOfFarmRequest;
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
    public List<KoiOfFarm> getAll() {
        return koiOfFarmRepository.findAll();
    }

    @Override
    public KoiOfFarm getById(Long Id) {
        KoiOfFarm koiOfFarm =koiOfFarmRepository.findById(Id)
                .orElseThrow(() -> new NotFoundEntity("Not found"));
        return koiOfFarm;
    }

    @Override
    public KoiOfFarm createKoiOfFarm(KoiOfFarmRequest koiOfFarmRequest) {
//        KoiOfFarm koiOfFarms = modelMapper.map(koiOfFarmRequest,KoiOfFarm.class);
        KoiOfFarm koiOfFarms =new KoiOfFarm();
        koiOfFarms.setQuantity(koiOfFarmRequest.getQuantity());
        koiOfFarms.setAvailable(koiOfFarmRequest.isAvailable());

        Kois kois = iKoisRepository.findById(koiOfFarmRequest.getKoiId())
                .orElseThrow(() -> new NotFoundEntity("NOT FOUND KOI ID"));
        koiOfFarms.setKois(kois);
        KoiFarms koiFarms =iKoiFarmsRepository.findById(koiOfFarmRequest.getFarmId())
                .orElseThrow(() -> new NotFoundEntity("Not found Farm Id"));
//        koiOfFarms.setKois(kois);
//        koiOfFarms.setKoiFarms(koiFarms);
        koiOfFarms.setKoiFarms(koiFarms);
        return koiOfFarmRepository.save(koiOfFarms);
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
        Kois kois = null;
        KoiFarms koiFarms = null;
        if (koiOfFarmRequest.getFarmId() != null) {
            koiFarms = iKoiFarmsRepository.findById(koiOfFarmRequest.getFarmId())
                    .orElseThrow(() -> new NotFoundEntity("Not found Farm Id"));
            koiOfFarm.setKoiFarms(koiFarms);
        }
        if(  koiOfFarmRequest.getKoiId() != null){
            kois = iKoisRepository.findById(koiOfFarmRequest.getKoiId())
                    .orElseThrow(() -> new NotFoundEntity("Not Found Koi Id"));
            koiOfFarm.setKois(kois);
        }
        koiOfFarm.setAvailable(koiOfFarmRequest.isAvailable());
        koiOfFarm.setQuantity(koiOfFarmRequest.getQuantity());
        return koiOfFarmRepository.save(koiOfFarm);
    }
}
