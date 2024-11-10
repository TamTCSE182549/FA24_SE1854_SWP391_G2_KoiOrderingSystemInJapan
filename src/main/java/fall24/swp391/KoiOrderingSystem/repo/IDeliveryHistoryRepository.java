    package fall24.swp391.KoiOrderingSystem.repo;

    import fall24.swp391.KoiOrderingSystem.pojo.Bookings;
    import fall24.swp391.KoiOrderingSystem.pojo.DeliveryHistory;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    @Repository
    public interface IDeliveryHistoryRepository extends JpaRepository<DeliveryHistory, Long> {
        DeliveryHistory findDeliveryHistoryById(Long id);
        List<DeliveryHistory> findDeliveryHistoryByBooking(Bookings booking);
        DeliveryHistory findFirstByBookingOrderByCreatedDateDesc(Bookings booking);
    }
