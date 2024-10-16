package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.model.request.KoiFarmRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiFarmImageResponse;
import fall24.swp391.KoiOrderingSystem.model.response.KoiFarmResponse;
import fall24.swp391.KoiOrderingSystem.model.response.KoiOfFarmResponse;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KoiFarmService implements IKoiFarmsService{

    @Autowired
    private IKoiFarmsRepository iKoiFarmsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public KoiFarmResponse createKoiFarm(KoiFarmRequest koiFarmRequest) {
        KoiFarms koiFarms = modelMapper.map(koiFarmRequest, KoiFarms.class);
        koiFarms.setActive(true);
        iKoiFarmsRepository.save(koiFarms);
        return modelMapper.map(koiFarms, KoiFarmResponse.class);
    }

    @Override
    public List<KoiFarms> listKoiFarm() {
        return iKoiFarmsRepository.findAll();
    }

    @Override
    public List<KoiFarmResponse> getFarmById(Long id) {
        List<KoiFarms> koiFarmsList = iKoiFarmsRepository.findFarmById(id);
        return koiFarmsList.stream().map(koiFarms -> {
            KoiFarmResponse koiFarmResponse = modelMapper.map(koiFarms, KoiFarmResponse.class);
            return koiFarmResponse;
        }).toList();
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
//                koiFarmToUpdate.setFarmImage(koiFarm.getFarmImage());
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
    public KoiFarmResponse updateKoiFarmRes(KoiFarmRequest koiFarmRequest, Long id) {
        KoiFarms koiFarms = iKoiFarmsRepository.findKoiFarmsById(id);
        if(koiFarms == null){
            throw new NotFoundEntity("Farm not found!");
        }
        koiFarms.setFarmName(koiFarmRequest.getFarmName());
        koiFarms.setFarmPhoneNumber(koiFarmRequest.getFarmPhoneNumber());
        koiFarms.setFarmEmail(koiFarmRequest.getFarmEmail());
        koiFarms.setFarmAddress(koiFarmRequest.getFarmAddress());
        koiFarms.setWebsite(koiFarmRequest.getWebsite());
        iKoiFarmsRepository.save(koiFarms);
        return modelMapper.map(koiFarms, KoiFarmResponse.class);
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

    //Khi delete thi Farm se tra ve gia tri false
    @Override
    public KoiFarmResponse deleteKoiFarmRes(Long id) {
        KoiFarms koiFarms = iKoiFarmsRepository.findKoiFarmsById(id);
        if(koiFarms == null){
            throw new NotFoundEntity("Farm not found!");
        }
        koiFarms.setActive(false);
        iKoiFarmsRepository.save(koiFarms);
        KoiFarmResponse koiFarmResponse = modelMapper.map(koiFarms, KoiFarmResponse.class);
        return koiFarmResponse;
    }

    @Override
    public List<KoiFarmResponse> getAllKoiFarms() {
        List<KoiFarms> koiFarmsList = iKoiFarmsRepository.findAll();
        return koiFarmsList.stream().map(this::convertToKoiFarmResponse).collect(Collectors.toList());
    }

    @Override
    public KoiFarmResponse convertToKoiFarmResponse(KoiFarms koiFarm) {
        KoiFarmResponse response = new KoiFarmResponse();
        response.setKoiFarmName(koiFarm.getFarmName());
        response.setKoiFarmPhone(koiFarm.getFarmPhoneNumber());
        response.setKoiFarmEmail(koiFarm.getFarmEmail());
        response.setKoiFarmAddress(koiFarm.getFarmAddress());
        response.setWebsite(koiFarm.getWebsite());

        List<KoiOfFarmResponse> koiOfFarmResponses = koiFarm.getKoiOfFarms().stream()
                .map(koiOfFarm -> {
                    KoiOfFarmResponse koiOfFarmResponse = new KoiOfFarmResponse();
                    koiOfFarmResponse.setKoiId(koiOfFarm.getKois().getId());
                    koiOfFarmResponse.setQuantity(koiOfFarm.getQuantity());
                    return koiOfFarmResponse;
                }).collect(Collectors.toList());

        response.setKoiOfFarms(koiOfFarmResponses);

        List<KoiFarmImageResponse> koiFarmImageResponses = koiFarm.getKoiFarmImages().stream()
                .map(koiFarmImage -> {
                    KoiFarmImageResponse koiFarmImageResponse = new KoiFarmImageResponse();
                    koiFarmImageResponse.setImageUrl(koiFarmImage.getImageUrl());
                    return koiFarmImageResponse;
                }).collect(Collectors.toList());
        response.setKoiFarmImages(koiFarmImageResponses);
        return response;
    }
}
