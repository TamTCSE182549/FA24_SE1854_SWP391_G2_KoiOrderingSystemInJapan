package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.Role;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.request.TourDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.TourDetailResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.pojo.TourDetail;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import fall24.swp391.KoiOrderingSystem.repo.ITourDetailRepository;
import fall24.swp391.KoiOrderingSystem.repo.ITourRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourDetailService implements ITourDetailService{

    @Autowired
    private ITourDetailRepository iTourDetailRepository;

    @Autowired
    private IKoiFarmsRepository iKoiFarmsRepository;

    @Autowired
    private ITourRepository iTourRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public List<TourDetailResponse> TOUR_RESPONSES_BY_FARM(Long farmID) {
        KoiFarms koiFarms = iKoiFarmsRepository.findById(farmID)
                .orElseThrow(() -> new NotFoundEntity("Koi Farm not EXISTS"));
        List<TourDetail> tourDetails = iTourDetailRepository.findByFarm_Id(koiFarms.getId());
        return tourDetails.stream().map(tourDetail -> {
            TourDetailResponse tourDetailResponse = modelMapper.map(tourDetail, TourDetailResponse.class);
            tourDetailResponse.setTourName(tourDetail.getTour().getTourName());
            tourDetailResponse.setFarmName(tourDetail.getFarm().getFarmName());
            return tourDetailResponse;
        }).toList();
    }

    @Override
    public List<TourDetailResponse> TOUR_DETAIL_RESPONSES_BY_TOUR(Long tourID) {
        Tours tours = iTourRepository.findById(tourID)
                .orElseThrow(() -> new NotFoundEntity("Tour not EXISTS"));
        List<TourDetail> tourDetails = iTourDetailRepository.findByTour_Id(tours.getId());
        return tourDetails.stream().map(tourDetail -> {
            TourDetailResponse tourDetailResponse = modelMapper.map(tourDetail, TourDetailResponse.class);
            tourDetailResponse.setTourName(tourDetail.getTour().getTourName());
            tourDetailResponse.setWebsite(tourDetail.getFarm().getWebsite());
            tourDetailResponse.setPhone(tourDetail.getFarm().getFarmPhoneNumber());
            tourDetailResponse.setDescription(tourDetail.getDescription());
            tourDetailResponse.setFarmName(tourDetailResponse.getFarmName());
            tourDetailResponse.setFarmId(tourDetail.getFarm().getId());
            return tourDetailResponse;
        }).toList();
    }

    @Override
    public TourDetailResponse CREATE_TOUR_DETAIL_RESPONSES(TourDetailRequest tourDetailRequest) {
        Account account = authenticationService.getCurrentAccount();
        if (account.getRole()!= Role.MANAGER){
            throw new NotFoundEntity("Your ROLE cannot access");
        }
        KoiFarms koiFarms = iKoiFarmsRepository.findById(tourDetailRequest.getFarmID())
                .orElseThrow(() -> new NotFoundEntity("Koi Farm not EXISTS"));
        Tours tours = iTourRepository.findById(tourDetailRequest.getTourID())
                .orElseThrow(() -> new NotFoundEntity("Tour not EXISTS"));
        TourDetail tourDetail = iTourDetailRepository.findByFarmAndTour(koiFarms, tours);
        if(tourDetail!=null){
            throw new NotFoundEntity("Tour already have farm");
        }
         tourDetail = new TourDetail(tours, koiFarms, tourDetailRequest.getDescription());
        iTourDetailRepository.save(tourDetail);
        TourDetailResponse tourDetailResponse = modelMapper.map(tourDetail, TourDetailResponse.class);
        tourDetailResponse.setTourName(tours.getTourName());
        tourDetailResponse.setFarmName(tourDetailResponse.getFarmName());
        return tourDetailResponse;
    }

    @Override
    public void DELETE_TOUR_DETAIL(Long tourDetailID) {
        Account account = authenticationService.getCurrentAccount();
        if (account.getRole()!= Role.MANAGER && account.getRole()!= Role.CUSTOMER){
            throw new NotFoundEntity("Your ROLE cannot access");
        }
        TourDetail tourDetail = iTourDetailRepository.findById(tourDetailID)
                .orElseThrow(() -> new NotFoundEntity("Tour Detail not FOUND to DELETE"));
        iTourDetailRepository.deleteById(tourDetail.getId());
    }

    @Override
    public TourDetailResponse UPDATE_TOUR_DETAIL_RESPONSE(Long tourDetailID) {
        return null;
    }
}
