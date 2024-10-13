package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.TourDetailRequest;
import fall24.swp391.KoiOrderingSystem.model.response.TourDetailResponse;

import java.util.Set;

public interface ITourDetailService {

    Set<TourDetailResponse> TOUR_RESPONSES_BY_FARM(Long farmID);

    Set<TourDetailResponse> TOUR_DETAIL_RESPONSES_BY_TOUR(Long tourID);

    TourDetailResponse CREATE_TOUR_DETAIL_RESPONSES(TourDetailRequest tourDetailRequest);

    void DELETE_TOUR_DETAIL(Long tourDetailID);

    TourDetailResponse UPDATE_TOUR_DETAIL_RESPONSE(Long tourDetailID);
}
