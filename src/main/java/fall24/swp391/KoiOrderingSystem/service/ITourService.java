package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.FindTourRequest;
import fall24.swp391.KoiOrderingSystem.model.request.TourRequest;
import fall24.swp391.KoiOrderingSystem.model.response.TourResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITourService {

    void createTour(Tours tours);

    TourResponse createTourRes(TourRequest tourRequest);

    List<Tours> tourList();

    void updateTour(Long id, Tours tours);

    TourResponse updateTourRes(Long id, TourRequest tourRequest);

    Tours deleteTourById(Long id);

    TourResponse deleteTourRes(Long tourID);

    List<TourResponse> tourResponseListActive();

    List<TourResponse> showAll();

    Page<TourResponse> showAllPageable(int page, int size);

    Page<TourResponse> showTourByName(int page, int size, String nameTour);

    Page<TourResponse> showTourByManyCondition(int page, int size, FindTourRequest findTourRequest);

    Page<TourResponse> showTourByFarmName(int page, int size, FindTourRequest findTourRequest);

    TourResponse findById(Long tourID);

    Page<TourResponse> findTourByKoiCategory(int page, int size, Integer categoryId);

    Page<TourResponse> findTourByKoiName(int page, int size, String koiName);

    Page<TourResponse> findTourByKoiNameAndFarmName(int page, int size, FindTourRequest findTourRequest);
}
