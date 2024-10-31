package fall24.swp391.KoiOrderingSystem.repo;


import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
import fall24.swp391.KoiOrderingSystem.pojo.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFeedbackRepository extends JpaRepository<Feedback,Long> {
    List<Feedback> findFeedbacksByBooking(Bookings booking);
    List<Feedback> findFeedbacksByCustomer(Account account);
}
