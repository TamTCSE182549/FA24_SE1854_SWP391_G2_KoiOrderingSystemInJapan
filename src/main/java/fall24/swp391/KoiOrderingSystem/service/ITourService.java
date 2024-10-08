package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.response.TourResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;

import java.util.List;

public interface ITourService {

    void createTour(Tours tours);

    List<Tours> tourList();

    void updateTour(Long id, Tours tours);

    Tours deleteTourById(Long id);

    List<TourResponse> tourResponseList();
}
