package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.exception.NotUpdateException;
import fall24.swp391.KoiOrderingSystem.model.request.KoiFarmRequest;
import fall24.swp391.KoiOrderingSystem.model.response.KoiFarmImageResponse;
import fall24.swp391.KoiOrderingSystem.model.response.KoiFarmResponse;
import fall24.swp391.KoiOrderingSystem.model.response.KoiOfFarmResponse;
import fall24.swp391.KoiOrderingSystem.model.response.KoiResponse;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarmImage;
import fall24.swp391.KoiOrderingSystem.pojo.KoiFarms;
import fall24.swp391.KoiOrderingSystem.pojo.KoiOfFarm;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmImageRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoiFarmsRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoiOfFarmRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoisRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KoiFarmService implements IKoiFarmsService{

    @Autowired
    private IKoiFarmsRepository iKoiFarmsRepository;

    @Autowired
    private IKoiFarmImageRepository koiFarmImageRepository;

    @Autowired
    private IKoiOfFarmRepository koiOfFarmRepository;

    @Autowired
    private IKoisRepository koisRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public KoiFarmResponse createKoiFarm(KoiFarmRequest koiFarmRequest) {
        KoiFarms koiFarms = modelMapper.map(koiFarmRequest, KoiFarms.class);
        koiFarms.setActive(true);
        List<KoiFarmImage> koiFarmImages = new ArrayList<>();
        for (String url : koiFarmRequest.getImages()) {
            KoiFarmImage koiFarmImage = new KoiFarmImage();
            koiFarmImage.setImageUrl(url);
            koiFarmImage.setKoiFarms(koiFarms);
            koiFarmImages.add(koiFarmImage);
        }
        koiFarms.setKoiFarmImages(koiFarmImages);
        iKoiFarmsRepository.save(koiFarms);
        return modelMapper.map(koiFarms, KoiFarmResponse.class);
    }

    @Override
    public List<KoiFarms> listKoiFarm() {
        return iKoiFarmsRepository.findAll();
    }

    @Override
    public KoiFarmResponse getFarmById(Long id) {
        KoiFarms koiFarms = iKoiFarmsRepository.findKoiFarmsById(id);
        if(koiFarms ==null){
            throw new NotFoundEntity("KoiFarms not found");
        }
        List<KoiOfFarm> koiOfFarms = koiOfFarmRepository.findKoiOfFarmBykoiFarms(koiFarms);
        koiFarms.setKoiOfFarms(koiOfFarms);
        KoiFarmResponse koiFarmResponse = modelMapper.map(koiFarms, KoiFarmResponse.class);
        List<KoiOfFarmResponse> koiOfFarmResponses = new ArrayList<>();
        List<KoiResponse> koiResponses = new ArrayList<>();
        for(KoiOfFarm koi : koiOfFarms){
            KoiOfFarmResponse koiOfFarmResponse = modelMapper.map(koi, KoiOfFarmResponse.class);
            koiOfFarmResponse.setKoi_id(koi.getKois().getId());
            koiOfFarmResponse.setFarm_id(koi.getKoiFarms().getId());
            koiOfFarmResponses.add(koiOfFarmResponse);
            Kois kois = koisRepository.findKoisById(koi.getKois().getId());
            KoiResponse koiResponse = modelMapper.map(kois,KoiResponse.class);
            koiResponses.add(koiResponse);
        }
        koiFarmResponse.setKoiOfFarms(koiOfFarmResponses);
        koiFarmResponse.setKoiResponses(koiResponses);
        return koiFarmResponse;
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
        response.setId(koiFarm.getId());
        response.setFarmName(koiFarm.getFarmName());
        response.setFarmPhoneNumber(koiFarm.getFarmPhoneNumber());
        response.setFarmEmail(koiFarm.getFarmEmail());
        response.setFarmAddress(koiFarm.getFarmAddress());
        response.setWebsite(koiFarm.getWebsite());

        List<KoiOfFarmResponse> koiOfFarmResponses = koiFarm.getKoiOfFarms().stream()
                .map(koiOfFarm -> {
                    KoiOfFarmResponse koiOfFarmResponse = new KoiOfFarmResponse();
                    koiOfFarmResponse.setKoi_id(koiOfFarm.getKois().getId());
                    koiOfFarmResponse.setQuantity(koiOfFarm.getQuantity());
                    koiOfFarmResponse.setFarm_id(koiOfFarm.getKoiFarms().getId());
                    koiOfFarmResponse.setAvailable(true);
                    koiOfFarmResponse.setId(koiOfFarm.getId());
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

    @Override
    public List<KoiFarmResponse> getFarmIsActive() {
        List<KoiFarms> koiFarmsList = iKoiFarmsRepository.findKoiFarmIsActive();
        return koiFarmsList.stream().map(this::convertToKoiFarmResponse).collect(Collectors.toList());
    }

    @Override
    public Page<KoiFarmResponse> showFarmByName(int page, int size, String farmName){
        Pageable pageable = PageRequest.of(page, size);
        return iKoiFarmsRepository.showFarmByName(farmName, pageable).map(koiFarms -> {
            KoiFarmResponse koiFarmResponse = modelMapper.map(koiFarms, KoiFarmResponse.class);
            koiFarmResponse.setFarmName(koiFarms.getFarmName());
            return koiFarmResponse;
        });
    }
}
