package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KoiFarmService implements IKoiFarmsService{

    @Autowired
    private IKoiFarmsRepository iKoiFarmsRepository;

    @Override
    public KoiFarms createKoiFarm(KoiFarms koiFarm) {
        return iKoiFarmsRepository.save(koiFarm);
    }

    @Override
    public List<KoiFarms> listKoiFarm() {
        return iKoiFarmsRepository.findAll();
    }

    @Override
    public KoiFarms updateKoiFarm(Long id, KoiFarms koiFarm) {
        try {
            KoiFarms koiFarmToUpdate = null;
            Optional<KoiFarms> existFarm = iKoiFarmsRepository.findById(id);
            if (existFarm.isPresent()){
                koiFarmToUpdate = existFarm.get();
                koiFarmToUpdate.setFarmAddress(koiFarm.getFarmAddress());
                koiFarmToUpdate.setFarmEmail(koiFarm.getFarmEmail());
                koiFarmToUpdate.setFarmImage(koiFarm.getFarmImage());
                koiFarmToUpdate.setFarmName(koiFarm.getFarmName());
                koiFarmToUpdate.setFarmPhoneNumber(koiFarm.getFarmPhoneNumber());
                koiFarmToUpdate.setWebsite(koiFarm.getWebsite());
                iKoiFarmsRepository.save(koiFarmToUpdate);
                return koiFarmToUpdate;
            } else {
                throw new NotUpdateException("Update Koi Farm - " + id + " failed");
            }
        } catch (Exception e){
            throw new NotUpdateException(e.getMessage());
        }
    }

    @Override
    public KoiFarms deleteKoiFarm(Long id) {
        try {
            KoiFarms koiFarmToUpdate = null;
            Optional<KoiFarms> existFarm = iKoiFarmsRepository.findById(id);
            if (existFarm.isPresent()){
                koiFarmToUpdate = existFarm.get();
                koiFarmToUpdate.setActive(false);
                iKoiFarmsRepository.save(koiFarmToUpdate);
                return koiFarmToUpdate;
            } else {
                throw new NotUpdateException("Delete Koi Farm - " + id + " failed");
            }
        } catch (Exception e){
            throw new NotUpdateException(e.getMessage());
        }
    }
}
