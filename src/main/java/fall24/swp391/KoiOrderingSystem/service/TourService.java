package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import fall24.swp391.KoiOrderingSystem.repo.ITourRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TourService implements ITourService{

    private ITourRepository iTourRepository;

    @Override
    public Tours createTour(Tours tours){
        return iTourRepository.save(tours);
    }

    @Override
    public boolean updateTour(Long id, Tours tours) {
        return false;
    }

    @Override
    public boolean deleteTour(Long id){
        Optional<Tours> tours = iTourRepository.findById(id);
        if(tours.isPresent()){
            iTourRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
