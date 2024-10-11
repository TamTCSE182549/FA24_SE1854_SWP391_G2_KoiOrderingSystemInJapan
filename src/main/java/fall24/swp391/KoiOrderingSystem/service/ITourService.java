package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.TourRequest;
import fall24.swp391.KoiOrderingSystem.model.response.TourResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;

import java.util.List;

public interface ITourService {

    void createTour(Tours tours);

    TourResponse createTourRes(TourRequest tourRequest);

    List<Tours> tourList();

    void updateTour(Long id, Tours tours);

    TourResponse updateTourRes(TourRequest tourRequest);

    Tours deleteTourById(Long id);

    TourResponse deleteTourRes(Long tourID);

    List<TourResponse> tourResponseList();
}
