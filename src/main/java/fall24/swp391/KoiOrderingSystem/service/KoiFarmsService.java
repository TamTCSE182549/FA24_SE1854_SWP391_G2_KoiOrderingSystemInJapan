package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;

import java.util.List;

public class KoiFarmsService implements  IKoiFarmsService{
    private IKoiFarmsRepository koiFarmsRepository;

    public void koiFarmsService(IKoiFarmsRepository koiFarmsRepo){
        koiFarmsRepository = koiFarmsRepo;
    }

    @Override
    public List<KoiFarms> findAll() {
        return koiFarmsRepository.findAll();
    }

    @Override
    public KoiFarms findById(Long Id) {
        return null;
    }

    @Override
    public KoiFarms save(KoiFarms koiFarms) {
        return koiFarmsRepository.save(koiFarms);
    }

    @Override
    public void deletebyId(Long Id) {
        koiFarmsRepository.deleteById(Id);
    }
}
