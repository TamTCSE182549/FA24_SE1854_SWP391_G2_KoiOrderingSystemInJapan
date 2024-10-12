package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import fall24.swp391.KoiOrderingSystem.repo.IKoiOfFarmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoiOfFarmService implements IKoiOfFarmService{
    private IKoiOfFarmRepository koiOfFarmRepository;

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
    public KoiOfFarm save(KoiOfFarm koiOfFarm) {
        return koiOfFarmRepository.save(koiOfFarm);
    }

    @Override
    public void deleteById(Long Id) {
        koiOfFarmRepository.deleteById(Id);
    }
}
