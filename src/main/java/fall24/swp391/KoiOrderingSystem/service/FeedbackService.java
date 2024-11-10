package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.BookingType;
import fall24.swp391.KoiOrderingSystem.exception.AccountNotFoundException;
import fall24.swp391.KoiOrderingSystem.exception.ExistingEntity;
import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.request.FeedbackRequest;
import fall24.swp391.KoiOrderingSystem.model.response.FeedbackResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Feedback;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import fall24.swp391.KoiOrderingSystem.repo.IBookingRepository;
import fall24.swp391.KoiOrderingSystem.repo.IFeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService implements IFeedbackService{
    @Autowired
    IFeedbackRepository feedbackRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    IBookingRepository bookingRepository;

    @Override
    public FeedbackResponse createFeedback(FeedbackRequest feedbackrequest) {
        Feedback feedback = new Feedback();
        Account account = authenticationService.getCurrentAccount();
        if(account == null) throw new AccountNotFoundException("Account not found");
        Bookings booking = bookingRepository.findBookingsById(feedbackrequest.getBookingId());
        if(booking == null) throw new NotFoundEntity("Booking not found");
        if(booking.getBookingType()== BookingType.BookingForKoi) throw new ExistingEntity("Feedback booking tour includes booing fish");
        if (feedbackRepository.existsByCustomerAndBooking(account, booking)) {
            throw new ExistingEntity("Feedback to this booking already exists");
        }
        Tours tour = booking.getBookingTourDetails().getFirst().getTourId();
        if (tour.getEndTime().isAfter(LocalDateTime.now())) {
            throw new ExistingEntity("You can only provide feedback after the tour has ended");
        }
        feedback.setRating(feedbackrequest.getRating());
        feedback.setContent(feedbackrequest.getContent());
        feedback.setBooking(booking);
        feedback.setCustomer(account);
         feedbackRepository.save(feedback);

         FeedbackResponse feedbackResponse = new FeedbackResponse();
         feedbackResponse.setBookingId(feedback.getBooking().getId());
         feedbackResponse.setCustomerName(account.getLastName()+" "+account.getFirstName());
         feedbackResponse.setContent(feedback.getContent());
         feedbackResponse.setRating(feedback.getRating());
         feedbackResponse.setId(feedback.getId());
         feedbackResponse.setCustomerId(account.getId());
         feedbackResponse.setCreatedDate(feedback.getCreatedDate());
         feedbackResponse.setUpdatedDate(feedback.getUpdatedDate());
         return feedbackResponse;

    }

    @Override
    public List<FeedbackResponse> getFeedbackByBooking(Long Bookingid) {
        Bookings booking = bookingRepository.findBookingsById(Bookingid);
        if(booking == null) {
            throw new NotFoundEntity("Booking not found");
        }
        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByBooking(booking);
        if(feedbacks == null) {
            throw new NotFoundEntity("Feedback not found");
        }
        List<FeedbackResponse> feedbackResponses = new ArrayList<>();
        for(Feedback feedback : feedbacks) {
            FeedbackResponse feedbackResponse = new FeedbackResponse();
            feedbackResponse.setBookingId(feedback.getBooking().getId());
            feedbackResponse.setCustomerName(feedback.getCustomer().getLastName()+" "+feedback.getCustomer().getFirstName());
            feedbackResponse.setContent(feedback.getContent());
            feedbackResponse.setRating(feedback.getRating());
            feedbackResponse.setId(feedback.getId());
            feedbackResponse.setCustomerId(feedback.getCustomer().getId());
            feedbackResponse.setCreatedDate(feedback.getCreatedDate());
            feedbackResponse.setUpdatedDate(feedback.getUpdatedDate());
            feedbackResponses.add(feedbackResponse);
        }
        return feedbackResponses;

    }

    @Override
    public List<FeedbackResponse> getFeedbackByCustomer() {
        Account account = authenticationService.getCurrentAccount();
        if(account == null) throw new AccountNotFoundException("Account not found");
        List<Feedback> feedbacks = feedbackRepository.findFeedbacksByCustomer(account);
        if(feedbacks == null) {
            throw new NotFoundEntity("Feedback not found");
        }

        List<FeedbackResponse> feedbackResponses = new ArrayList<>();
        for(Feedback feedback : feedbacks) {
            FeedbackResponse feedbackResponse = new FeedbackResponse();
            feedbackResponse.setBookingId(feedback.getBooking().getId());
            feedbackResponse.setCustomerName(feedback.getCustomer().getLastName()+" "+feedback.getCustomer().getFirstName());
            feedbackResponse.setContent(feedback.getContent());
            feedbackResponse.setRating(feedback.getRating());
            feedbackResponse.setId(feedback.getId());
            feedbackResponse.setCustomerId(feedback.getCustomer().getId());
            feedbackResponse.setCreatedDate(feedback.getCreatedDate());
            feedbackResponse.setUpdatedDate(feedback.getUpdatedDate());
            feedbackResponses.add(feedbackResponse);
        }
        return feedbackResponses;
    }

    @Override
    public List<FeedbackResponse> getAllFeedback() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        List<FeedbackResponse> feedbackResponses = new ArrayList<>();
        for(Feedback feedback : feedbacks) {
            FeedbackResponse feedbackResponse = new FeedbackResponse();
            feedbackResponse.setBookingId(feedback.getBooking().getId());
            feedbackResponse.setCustomerName(feedback.getCustomer().getLastName()+" "+feedback.getCustomer().getFirstName());
            feedbackResponse.setContent(feedback.getContent());
            feedbackResponse.setRating(feedback.getRating());
            feedbackResponse.setId(feedback.getId());
            feedbackResponse.setCustomerId(feedback.getCustomer().getId());
            feedbackResponse.setCreatedDate(feedback.getCreatedDate());
            feedbackResponse.setUpdatedDate(feedback.getUpdatedDate());
            feedbackResponses.add(feedbackResponse);
        }
        return feedbackResponses;
    }
    @Transactional
    @Override
    public void deleteFeedback(Long id) {
        // Kiểm tra xem feedback có tồn tại không
        if (!feedbackRepository.existsById(id)) {
            throw new NotFoundEntity("Feedback not found");
        }
        // Xóa trực tiếp
        feedbackRepository.deleteById(id);
    }

    @Override
    public FeedbackResponse updateFeedback(FeedbackRequest feedbackRequest,Long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() ->
                new NotFoundEntity("Feedback not found")
        );
        feedback.setRating(feedbackRequest.getRating());
        feedback.setContent(feedbackRequest.getContent());
        feedbackRepository.save(feedback);
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setBookingId(feedback.getBooking().getId());
        feedbackResponse.setCustomerName(feedback.getCustomer().getLastName()+" "+feedback.getCustomer().getFirstName());
        feedbackResponse.setContent(feedback.getContent());
        feedbackResponse.setRating(feedback.getRating());
        feedbackResponse.setId(feedback.getId());
        feedbackResponse.setCustomerId(feedback.getCustomer().getId());
        feedbackResponse.setCreatedDate(feedback.getCreatedDate());
        feedbackResponse.setUpdatedDate(feedback.getUpdatedDate());
        return feedbackResponse;
    }
}
