package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.enums.Role;
import fall24.swp391.KoiOrderingSystem.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService implements IAdminService {

    @Autowired
    IKoisRepository koisRepo;

    @Autowired
    IAccountRepository accountRepo;

    @Autowired
    IBookingRepository bookingRepo;

    @Autowired
    IKoiFarmsRepository koiFarmsRepo;
    @Autowired
    ITourRepository tourRepository;

    @Override
    public Map<String, Object> getDashboardStats() {
        //tong so koi
        Map<String, Object> stats = new HashMap<>();

        long totalKoi = koisRepo.count();
        stats.put("totalKoi", totalKoi);

        //so luong customer
        long totalCustomer = accountRepo.countByRole(Role.CUSTOMER);
        stats.put("totalCustomer", totalCustomer);

        //so luong farm
        long totalFarm = koiFarmsRepo.count();
        stats.put("totalFarm", totalFarm);

        long totalTour = tourRepository.count();
        stats.put("totalTOur", totalTour);

        //top5 tour ban chay
        List<Map<String,Object>> list = new ArrayList<>();
        List<Object[]> topKoi = koisRepo.findTop5BestSellingKoi();
        for (Object[] o : topKoi) {
            Map<String,Object> map = new HashMap<>();
            map.put("name", o[0]);
            map.put("totalSold", o[1]);
            list.add(map);
        }
        stats.put("topKoi", list);

        return stats;
    }

    @Override
    public Map<String, Object> getMonthlyRevenue() {
        Map<String, Object> Revenue = new HashMap<>();
        //lay doanh thu
        List<Object[]> monthlyRevenue = bookingRepo.calculatingTotalAmountWithVAT();
        List<Map<String,Object>> list = new ArrayList<>();
        for (Object[] o : monthlyRevenue) {
            Map<String,Object> map = new HashMap<>();
            map.put("year", o[0]);
            map.put("month", o[1]);
            map.put("totalAmount", o[2]);
            list.add(map);
        }
        Revenue.put("monthlyRevenue", list);
        return Revenue;
    }
}
