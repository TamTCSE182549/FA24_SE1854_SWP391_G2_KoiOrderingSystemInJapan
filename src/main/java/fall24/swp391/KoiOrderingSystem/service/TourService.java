package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.TourStatus;
import fall24.swp391.KoiOrderingSystem.exception.NotDeleteException;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import fall24.swp391.KoiOrderingSystem.repo.ITourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService implements ITourService{

    @Autowired
    private ITourRepository iTourRepository;

    @Override
    public void createTour(Tours tours){
        iTourRepository.save(tours);
    }

    @Override
    public List<Tours> tourList(){
        return iTourRepository.findAll();
    }

    @Override
    public void updateTour(Long id, Tours tours) {
        Optional<Tours> existTourOptional = iTourRepository.findById(id);
        if (existTourOptional.isPresent()) {
            Tours existTour = existTourOptional.get();
            try {
                existTour.setUnitPrice(tours.getUnitPrice());
                existTour.setMaxParticipants(tours.getMaxParticipants());
                existTour.setStartTime(tours.getStartTime());
                existTour.setEndTime(tours.getEndTime());
                existTour.setTourImg(tours.getTourImg());
                existTour.setStatus(tours.getStatus());
                iTourRepository.save(existTour);
            } catch (NotUpdateException e) {
                throw new NotUpdateException("Update details failed");
            }
        } else {
            throw new NotUpdateException("Tour with ID " + id + " does not exist.");
        }
    }

    @Override
    public Tours deleteTourById(Long id) {
        Optional<Tours> tours = iTourRepository.findById(id);
        if(tours.isPresent()){
            Tours tourUpdated = tours.get();
            tourUpdated.setStatus(TourStatus.inactive);
            return iTourRepository.save(tourUpdated);
        } else {
            throw new NotDeleteException("Cannot delete tour");
        }
    }
}
