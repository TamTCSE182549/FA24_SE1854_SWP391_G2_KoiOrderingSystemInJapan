package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.TourStatus;
import fall24.swp391.KoiOrderingSystem.exception.GenericException;
import fall24.swp391.KoiOrderingSystem.exception.NotDeleteException;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.model.request.TourRequest;
import fall24.swp391.KoiOrderingSystem.model.response.TourResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import fall24.swp391.KoiOrderingSystem.repo.ITourRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService implements ITourService{

    @Autowired
    private ITourRepository iTourRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void createTour(Tours tours){
        iTourRepository.save(tours);
    }

    @Override
    public TourResponse createTourRes(TourRequest tourRequest) {
//        Account account = authenticationService.getCurrentAccount();

        Tours tours = modelMapper.map(tourRequest, Tours.class);
//        tours.setCreatedBy(account);
        iTourRepository.save(tours);
        TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
//        tourResponse.setCreatedBy(account.getFirstName() + " " + account.getLastName());
        return tourResponse;
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
    public TourResponse updateTourRes(TourRequest tourRequest) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account == null){
                throw new NotFoundEntity("Account is empty");
            }
            Tours tours = modelMapper.map(tourRequest, Tours.class);
            tours.setUpdatedBy(account);
            iTourRepository.save(tours);
            return modelMapper.map(tours, TourResponse.class);
        } catch (Exception e){
            throw new GenericException(e.getMessage());
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

    @Override
    public TourResponse deleteTourRes(Long tourID) {
        Tours tours = iTourRepository.findById(tourID)
                .orElseThrow(() -> new NotFoundEntity("Tour to delete not FOUND"));
        Account account = authenticationService.getCurrentAccount();
        if (account == null) {
            throw new NotFoundEntity("Account is EMPTY");
        }
        tours.setStatus(TourStatus.inactive);
        tours.setUpdatedBy(account);
        iTourRepository.save(tours);
        TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
        tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
        tourResponse.setUpdatedBy(account.getFirstName() + " " + account.getLastName());
        return tourResponse;
    }

    @Override
    public List<TourResponse> tourResponseList() {
        List<Tours> toursList = iTourRepository.findAll();
        return toursList.stream()
                .map(tours -> {
                    TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
                    tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
                    return tourResponse;
                }).toList();
    }
}
