package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.Role;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.request.KoiFarmDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiFarmDetailResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmDetail;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmDetailRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KoiFarmDetailService implements IKoiFarmDetailService{

    @Autowired
    private IKoiFarmDetailRepository iKoiFarmDetailRepository;

    @Autowired
    private IKoiFarmsRepository iKoiFarmsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public List<KoiFarmDetailResponse> farmDetailByFarm(Long farmID){
        KoiFarms koiFarms = iKoiFarmsRepository.findById(farmID).
                orElseThrow(() -> new NotFoundEntity("Farm not exist"));
        List<KoiFarmDetail> koiFarmDetails = iKoiFarmDetailRepository.findByFarm_Id(koiFarms.getId());
        return koiFarmDetails.stream().map(koiFarmDetail -> {
            KoiFarmDetailResponse koiFarmDetailResponse = modelMapper.map(koiFarmDetail,KoiFarmDetailResponse.class);
            return koiFarmDetailResponse;
        }).toList();
    }

    @Override
    public KoiFarmDetailResponse createFarmDetailRes(KoiFarmDetailRequest koiFarmDetailRequest){
        Account account = authenticationService.getCurrentAccount();
        if (account.getRole()!= Role.MANAGER){
            throw new NotFoundEntity("Your ROLE cannot access");
        }
        KoiFarms koiFarms = iKoiFarmsRepository.findById(koiFarmDetailRequest.getFarmID()).
                orElseThrow(() -> new NotFoundEntity("Koi Farm not exist"));
        KoiFarmDetail koiFarmDetail = new KoiFarmDetail(koiFarms,koiFarmDetailRequest.getDescription());
        iKoiFarmDetailRepository.save(koiFarmDetail);
        KoiFarmDetailResponse koiFarmDetailResponse = modelMapper.map(koiFarmDetail, KoiFarmDetailResponse.class);
        koiFarmDetailResponse.setDescription(koiFarms.getDescription());
        return koiFarmDetailResponse;
    }

    @Override
    public void deleteFarmDetail(Long farmDetailID){
        Account account = authenticationService.getCurrentAccount();
        if (account.getRole()!= Role.MANAGER){
            throw new NotFoundEntity("Your ROLE cannot access");
        }
        KoiFarmDetail koiFarmDetail = iKoiFarmDetailRepository.findById(farmDetailID).orElseThrow(
                () -> new NotFoundEntity("Farm Detail Not found")
        );
        iKoiFarmDetailRepository.deleteById(koiFarmDetail.getId());
    }

    @Override
    public KoiFarmDetailResponse updateKoiFarmDetailRes(Long farmDetailID, KoiFarmDetailRequest koiFarmDetailRequest){
        Account account = authenticationService.getCurrentAccount();
        if (account.getRole()!= Role.MANAGER){
            throw new NotFoundEntity("Your ROLE cannot access");
        }
        KoiFarmDetail koiFarmDetail = iKoiFarmDetailRepository.findById(farmDetailID).orElseThrow(
                () -> new NotFoundEntity("Farm Detail Not found")
        );
        koiFarmDetail.setDescription(koiFarmDetailRequest.getDescription());
        iKoiFarmDetailRepository.save(koiFarmDetail);
        return modelMapper.map(koiFarmDetail, KoiFarmDetailResponse.class);
    }
}
