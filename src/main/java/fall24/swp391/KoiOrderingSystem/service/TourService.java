package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.Role;
import fall24.swp391.KoiOrderingSystem.enums.TourStatus;
import fall24.swp391.KoiOrderingSystem.exception.*;
import fall24.swp391.KoiOrderingSystem.model.request.FindTourRequest;
import fall24.swp391.KoiOrderingSystem.model.request.TourRequest;
import fall24.swp391.KoiOrderingSystem.model.request.TourRequestAdmin;
import fall24.swp391.KoiOrderingSystem.model.response.TourResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import fall24.swp391.KoiOrderingSystem.repo.ITourRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole()!= Role.MANAGER){
                throw new NotCreateException("Your role cannot access");
            }
            Tours tours = modelMapper.map(tourRequest, Tours.class);
            tours.setCreatedBy(account);
            tours.setRemaining(tours.getMaxParticipants());
            tours.setStatus(TourStatus.active);
            iTourRepository.save(tours);
            TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
            tourResponse.setMessage("Create Tour " + tourResponse.getTourName() + " Success");
            tourResponse.setCreatedBy(account.getFirstName() + " " + account.getLastName());
            return tourResponse;
        } catch (Exception e){
            throw new GenericException(e.getMessage());
        }
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
    public TourResponse updateTourRes(Long id, TourRequest tourRequest) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole()!= Role.MANAGER){
                throw new NotCreateException("Your role cannot access");
            }
            Tours tours = iTourRepository.findById(id)
                    .orElseThrow(() -> new NotFoundEntity("Tour not FOUND to UPDATE"));
            tours.setTourName(tourRequest.getTourName());
            tours.setUnitPrice(tourRequest.getUnitPrice());
            tours.setMaxParticipants(tourRequest.getMaxParticipants());
            tours.setDescription(tourRequest.getDescription());
            tours.setStartTime(tourRequest.getStartTime());
            tours.setEndTime(tourRequest.getEndTime());
            tours.setTourImg(tourRequest.getTourImg());
            tours.setUpdatedBy(account);
            iTourRepository.save(tours);
            TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
            tourResponse.setMessage("Update Tour " + tourResponse.getTourName() + " Success");
            tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
            tourResponse.setUpdatedBy(tours.getUpdatedBy().getFirstName() + " " + tours.getUpdatedBy().getLastName());
            return tourResponse;
        } catch (Exception e){
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public TourResponse updateTourAdmin(Long id, TourRequestAdmin tourRequest) {
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole()!= Role.MANAGER){
                throw new NotCreateException("Your role cannot access");
            }
            Tours tours = iTourRepository.findById(id)
                    .orElseThrow(() -> new NotFoundEntity("Tour not FOUND to UPDATE"));
            tours.setTourName(tourRequest.getTourName());
            tours.setUnitPrice(tourRequest.getUnitPrice());
            tours.setMaxParticipants(tourRequest.getMaxParticipants());
            tours.setDescription(tourRequest.getDescription());
            tours.setStartTime(tourRequest.getStartTime());
            tours.setEndTime(tourRequest.getEndTime());
            tours.setTourImg(tourRequest.getTourImg());
            tours.setStatus(tourRequest.getStatus());
            tours.setUpdatedBy(account);
            iTourRepository.save(tours);
            TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
            tourResponse.setMessage("Update Tour " + tourResponse.getTourName() + " Success");
            tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
            tourResponse.setUpdatedBy(tours.getUpdatedBy().getFirstName() + " " + tours.getUpdatedBy().getLastName());
            return tourResponse;
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
        try {
            Account account = authenticationService.getCurrentAccount();
            if (account.getRole()!= Role.MANAGER){
                throw new NotCreateException("Your role cannot access");
            }
            Tours tours = iTourRepository.findById(tourID)
                    .orElseThrow(() -> new NotFoundEntity("Tour to delete not FOUND"));
            tours.setStatus(TourStatus.inactive);
            tours.setUpdatedBy(account);
            iTourRepository.save(tours);
            TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
            tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
            tourResponse.setUpdatedBy(account.getFirstName() + " " + account.getLastName());
            return tourResponse;
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public List<TourResponse> tourResponseListActive() {
        List<Tours> toursList = iTourRepository.findAllByStatusActive();
        return toursList.stream()
                .map(tours -> {
                    TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
                    tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
                    if (tours.getUpdatedBy()!=null){
                        tourResponse.setUpdatedBy(tours.getUpdatedBy().getFirstName() + " " + tours.getUpdatedBy().getLastName());
                    } else {
                        tourResponse.setUpdatedBy("");
                    }
                    return tourResponse;
                }).toList();
    }

    public List<TourResponse> showAll() {
        List<Tours> toursList = iTourRepository.findAll();
        return toursList.stream()
                .map(tours -> {
                    TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
                    tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
                    if (tours.getUpdatedBy()!=null){
                        tourResponse.setUpdatedBy(tours.getUpdatedBy().getFirstName() + " " + tours.getUpdatedBy().getLastName());
                    } else {
                        tourResponse.setUpdatedBy("");
                    }
                    return tourResponse;
                }).toList();
    }

    @Override
    public Page<TourResponse> showAllPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return iTourRepository.showAllPageable(pageable).map(tours -> {
            TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
            tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
            if (tours.getUpdatedBy()!=null){
                tourResponse.setUpdatedBy(tours.getUpdatedBy().getFirstName() + " " + tours.getUpdatedBy().getLastName());
            } else {
                tourResponse.setUpdatedBy("");
            }
            return tourResponse;
        });
    }

    @Override
    public Page<TourResponse> showTourByName(int page, int size, String nameTour) {
        Pageable pageable = PageRequest.of(page, size);
        return iTourRepository.showTourByName("%" + nameTour + "%", pageable).map(tours -> {
            TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
            if (tours.getCreatedBy()!=null){
                tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
            } else {
                tourResponse.setCreatedBy("");
            }
            tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
            if (tours.getUpdatedBy()!=null){
                tourResponse.setUpdatedBy(tours.getUpdatedBy().getFirstName() + " " + tours.getUpdatedBy().getLastName());
            } else {
                tourResponse.setUpdatedBy("");
            }
            return tourResponse;
        });
    }

    @Override
    public Page<TourResponse> showTourByManyCondition(int page, int size, FindTourRequest findTourRequest) {
        return null;
    }

    @Override
    public Page<TourResponse> showTourByFarmName(int page, int size, FindTourRequest findTourRequest) {
        Pageable pageable = PageRequest.of(page, size);
        return iTourRepository.findTourByFarmName(findTourRequest.getFarmId(), pageable).map(tours -> {
            TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
            if (tours.getCreatedBy()!=null){
                tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
            } else {
                tourResponse.setCreatedBy("");
            }
            tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
            if (tours.getUpdatedBy()!=null){
                tourResponse.setUpdatedBy(tours.getUpdatedBy().getFirstName() + " " + tours.getUpdatedBy().getLastName());
            } else {
                tourResponse.setUpdatedBy("");
            }
            return tourResponse;
        });
    }

    @Override
    public TourResponse findById(Long tourID) {
        Tours tours = iTourRepository.findById(tourID).
                orElseThrow(() -> new NotFoundEntity("Tour ID not FOUND"));
        TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
        tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
        if (tours.getUpdatedBy()!=null){
            tourResponse.setUpdatedBy(tours.getUpdatedBy().getFirstName() + " " + tours.getUpdatedBy().getLastName());
        } else {
            tourResponse.setUpdatedBy("");
        }
        return tourResponse;
    }

    @Override
    public Page<TourResponse> findTourByKoiCategory(int page, int size, Integer categoryId) {
        Pageable pageable = PageRequest.of(page, size);
        return iTourRepository.findTourByKoiCategory(categoryId, pageable).map(tours -> {
            TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
            tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
            if (tours.getUpdatedBy()!=null){
                tourResponse.setUpdatedBy(tours.getUpdatedBy().getFirstName() + " " + tours.getUpdatedBy().getLastName());
            } else {
                tourResponse.setUpdatedBy("");
            }
            return tourResponse;
        });
    }

    @Override
    public Page<TourResponse> findTourByKoiName(int page, int size, String koiName) {
        Pageable pageable = PageRequest.of(page, size);
        return iTourRepository.findTourByKoiName("%" + koiName + "%", pageable).map(tours -> {
            TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
            if (tours.getCreatedBy()!=null){
                tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
            } else {
                tourResponse.setCreatedBy("");
            }
            if (tours.getUpdatedBy()!=null){
                tourResponse.setUpdatedBy(tours.getUpdatedBy().getFirstName() + " " + tours.getUpdatedBy().getLastName());
            } else {
                tourResponse.setUpdatedBy("");
            }
            return tourResponse;
        });
    }

    @Override
    public Page<TourResponse> findTourByCondition(int page, int size, FindTourRequest findTourRequest) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tours> tourResponses = null;
        try {
            if((findTourRequest.getKoiId()!=null||findTourRequest.getFarmId()!=null) && findTourRequest.getMinPrice() != null && findTourRequest.getMaxPrice() != null && findTourRequest.getStartDate() != null && findTourRequest.getEndDate() != null){
                tourResponses = iTourRepository.findTourByKoiNameAndByFarmNameWithAllCondition(findTourRequest.getKoiId(), findTourRequest.getFarmId(), findTourRequest.getMinPrice(), findTourRequest.getMaxPrice(), findTourRequest.getStartDate(), findTourRequest.getEndDate(), pageable);
            } else if((findTourRequest.getKoiId()!=null||findTourRequest.getFarmId()!=null) && findTourRequest.getMinPrice() != null && findTourRequest.getMaxPrice() != null){
                tourResponses = iTourRepository.findTourByKoiNameAndByFarmNameWithUnitPrice(findTourRequest.getKoiId(), findTourRequest.getFarmId(), findTourRequest.getMinPrice(), findTourRequest.getMaxPrice(), pageable);
            } else if ((findTourRequest.getKoiId()!=null||findTourRequest.getFarmId()!=null) && findTourRequest.getStartDate() != null && findTourRequest.getEndDate() != null) {
                tourResponses = iTourRepository.findTourByKoiNameAndByFarmNameWithDate(findTourRequest.getKoiId(), findTourRequest.getFarmId(), findTourRequest.getStartDate(), findTourRequest.getEndDate(), pageable);
            } else if (findTourRequest.getKoiId()!=null||findTourRequest.getFarmId()!=null){
                tourResponses = iTourRepository.findTourByKoiNameAndByFarmName(findTourRequest.getKoiId(), findTourRequest.getFarmId(), pageable);
            } else if(findTourRequest.getMinPrice()!=null && findTourRequest.getMaxPrice()!=null && findTourRequest.getStartDate() != null && findTourRequest.getEndDate() != null ) {
                tourResponses = iTourRepository.findTourByPriceAndDatePageable(findTourRequest.getMinPrice(), findTourRequest.getMaxPrice(), findTourRequest.getStartDate(), findTourRequest.getEndDate(), pageable);
            } else if(findTourRequest.getMinPrice()!=null && findTourRequest.getMaxPrice() != null) {
                tourResponses = iTourRepository.findTourByPricePageable(findTourRequest.getMinPrice(), findTourRequest.getMaxPrice(), pageable);
            } else if(findTourRequest.getStartDate()!=null && findTourRequest.getEndDate() != null){
                tourResponses = iTourRepository.findTourByDatePageable(findTourRequest.getStartDate(), findTourRequest.getEndDate(), pageable);
            } else {
                tourResponses = iTourRepository.showAllPageable(pageable);
            }

            return tourResponses.map(tours -> {
                TourResponse tourResponse = modelMapper.map(tours, TourResponse.class);
                if (tours.getCreatedBy()!=null){
                    tourResponse.setCreatedBy(tours.getCreatedBy().getFirstName() + " " + tours.getCreatedBy().getLastName());
                } else {
                    tourResponse.setCreatedBy("");
                }
                if (tours.getUpdatedBy()!=null){
                    tourResponse.setUpdatedBy(tours.getUpdatedBy().getFirstName() + " " + tours.getUpdatedBy().getLastName());
                } else {
                    tourResponse.setUpdatedBy("");
                }
                return tourResponse;
            });
        } catch (GenericException e) {
            throw new GenericException(e.getMessage());
        }
    }
}
