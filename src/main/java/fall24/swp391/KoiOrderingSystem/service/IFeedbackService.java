package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.FeedbackRequest;
import fall24.swp391.KoiOrderingSystem.model.response.FeedbackResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IFeedbackService {
    public FeedbackResponse createFeedback(FeedbackRequest feedback);

    public List<FeedbackResponse> getFeedbackByBooking(Long Bookingid);

    public List<FeedbackResponse> getFeedbackByCustomer();

    public List<FeedbackResponse> getAllFeedback();

    public void deleteFeedback(Long id);

    public FeedbackResponse updateFeedback(FeedbackRequest feedback,Long id);
}
