package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Tours;

public interface ITourService {

    Tours createTour(Tours tours);

    boolean updateTour(Long id, Tours tours);

    boolean deleteTour(Long id);
}
