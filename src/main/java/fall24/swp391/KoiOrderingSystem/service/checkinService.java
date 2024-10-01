package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Checkin;
import fall24.swp391.KoiOrderingSystem.repo.ICheckinRepository;

import java.util.List;

public class checkinService implements ICheckinService{

    private ICheckinRepository checkinRepository;

    public checkinService(ICheckinRepository thecheckinRepository) {
        checkinRepository = thecheckinRepository;
    }

    @Override
    public List<Checkin> findAll() {
        return checkinRepository.findAll();
    }

    @Override
    public Checkin findById(Long Id) {
        return null;
    }

    @Override
    public Checkin save(Checkin checkin) {
        return checkinRepository.save(checkin);
    }

    @Override
    public void deletebyId(Long theId) {
        checkinRepository.deleteById(theId);
    }
}
