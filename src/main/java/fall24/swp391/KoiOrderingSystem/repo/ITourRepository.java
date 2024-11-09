package fall24.swp391.KoiOrderingSystem.repo;

import fall24.swp391.KoiOrderingSystem.model.response.TourResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Tours;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ITourRepository extends JpaRepository<Tours, Long> {
    @Query(value = "select * from tours where status = 'active'", nativeQuery = true)
    List<Tours> findAllByStatusActive();

    @Query(value = "SELECT * FROM Tours", nativeQuery = true)
    Page<Tours> showAllPageable(Pageable pageable);

    @Query(value = "select * from tours where tour_name like ?1", nativeQuery = true)
    Page<Tours> showTourByName(String nameTour, Pageable pageable);

    @Query(value = "select f.* from tours t, kois k, koi_of_farm kf, categories c, koi_farms f where c.id = ?1 and c.id = koi.category_id and koi.id = kf.koi_id and kf.farm_id = f.id", nativeQuery = true)
    Page<Tours> findTourByKoiCategory(Integer categoryId, Pageable pageable);

    @Query(value = "select distinct t.* " +
            "from tours t, tour_detail td, koi_farms kf, koi_of_farm kof, kois k " +
            "where k.koi_name like ?1 and t.id = td.tour_id and td.farm_id = kf.id and kf.id = kof.farm_id and kof.koi_id = k.id", nativeQuery = true)
    Page<Tours> findTourByKoiName(String koiName, Pageable pageable);

    @Query(value = "select distinct t.* " +
            "from tours t, koi_farms kf, tour_detail td " +
            "where kf.farm_name like ?1 and kf.id = td.farm_id and td.tour_id = t.id", nativeQuery = true)
    Page<Tours> findTourByFarmName(Long farmId, Pageable pageable);

    @Query(value = "select distinct t.* " +
            "from tours t, tour_detail td, koi_farms kf, koi_of_farm kof, kois k " +
            "where k.koi_name like ?1 and t.id = td.tour_id and td.farm_id = kf.id and kf.id = kof.farm_id and kof.koi_id = k.id", nativeQuery = true)
    Page<Tours> findTourByKoiNameWithCondition(String koiName, Float minPrice, Float maxPrice, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "select distinct t.* " +
            "from tours t, koi_farms kf, tour_detail td " +
            "where kf.farm_name like ?1 and kf.id = td.farm_id and td.tour_id = t.id", nativeQuery = true)
    Page<Tours> findTourByFarmNameWithCondition(Long farmId, Long koiId, Float minPrice, Float maxPrice, Date startDate, Date endDate, Pageable pageable);



    @Query(value = "select distinct t.* " +
            "from tours t, tour_detail td, koi_farms kf, koi_of_farm kof, kois k " +
            "where k.id = ?1 and t.id = td.tour_id and td.farm_id = kf.id and kf.id = kof.farm_id and kof.koi_id = k.id", nativeQuery = true)
    List<Tours> findTourByKoiName(Long koiId);

    @Query(value = "select distinct t.* " +
            "from tours t, koi_farms kf, tour_detail td " +
            "where kf.id = ?1 and kf.id = td.farm_id and td.tour_id = t.id", nativeQuery = true)
    List<Tours> findTourByFarmName(Long farmId);

    @Query(value = "select distinct t.* " +
            "from tours t, tour_detail td, koi_farms kf, koi_of_farm kof, kois k " +
            "where k.id = ?1 and t.id = td.tour_id and td.farm_id = kf.id and kf.id = kof.farm_id and kof.koi_id = k.id and t.unit_price >= ?2 and t.unit_price <= ?3", nativeQuery = true)
    List<Tours> findTourByKoiNameWithPrice(Long koiId, Float minPrice, Float maxPrice);

    @Query(value = "select distinct t.* " +
            "from tours t, koi_farms kf, tour_detail td " +
            "where kf.id = ?1 and kf.id = td.farm_id and td.tour_id = t.id and t.unit_price >= ?2 and t.unit_price <= ?3", nativeQuery = true)
    List<Tours> findTourByFarmNameWithPrice(Long farmId, Float minPrice, Float maxPrice);

    @Query(value = "select distinct t.* " +
            "from tours t, tour_detail td, koi_farms kf, koi_of_farm kof, kois k " +
            "where k.id = ?1 and t.id = td.tour_id and td.farm_id = kf.id and kf.id = kof.farm_id and kof.koi_id = k.id and date(t.start_time) >= ?2 and date(t.end_time) <= ?3", nativeQuery = true)
    List<Tours> findTourByKoiNameWithDate(Long koiId, String startDate, String endDate);

    @Query(value = "select distinct t.* " +
            "from tours t, koi_farms kf, tour_detail td " +
            "where kf.id = ?1 and kf.id = td.farm_id and td.tour_id = t.id and date(t.start_time) >= ?2 and date(t.end_time) <= ?3", nativeQuery = true)
    List<Tours> findTourByFarmNameWithDate(Long farmId, String startDate, String endDate);

    @Query(value = "select distinct t.* " +
            "from tours t, tour_detail td, koi_farms kf, koi_of_farm kof, kois k " +
            "where k.id = ?1 and t.id = td.tour_id and td.farm_id = kf.id and kf.id = kof.farm_id and kof.koi_id = k.id and t.unit_price >= ?2 and t.unit_price <= ?3 and date(t.start_time) >= ?4 and date(t.end_time) <= ?5", nativeQuery = true)
    List<Tours> findTourByKoiNameWithAllCondition(Long koiId, Float minPrice, Float maxPrice, String startDate, String endDate);

    @Query(value = "select distinct t.* " +
            "from tours t, koi_farms kf, tour_detail td " +
            "where kf.id = ?1 and kf.id = td.farm_id and td.tour_id = t.id and t.unit_price >= ?2 and t.unit_price <= ?3 and date(t.start_time) >= ?4 and date(t.end_time) <= ?5", nativeQuery = true)
    List<Tours> findTourByFarmNameWithAllCondition(Long farmId, Float minPrice, Float maxPrice, String startDate, String endDate);

    @Query(value = "select * " +
            "from tours t " +
            "where t.unit_price >= ?1 and t.unit_price <= ?2", nativeQuery = true)
    List<Tours> findTourByPrice(Float minPrice, Float maxPrice);

    @Query(value = "select * " +
            "from tours t " +
            "where date(t.start_time) >= ?1 and date(t.end_time) <= ?2", nativeQuery = true)
    List<Tours> findTourByDate(String startDate, String endDate);

    @Query(value = "select * " +
            "from tours t " +
            "where t.unit_price >= ?1 and t.unit_price <= ?2", nativeQuery = true)
    Page<Tours> findTourByPricePageable(Float minPrice, Float maxPrice, Pageable pageable);

    @Query(value = "select * " +
            "from tours t " +
            "where date(t.start_time) >= ?1 and date(t.end_time) <= ?2", nativeQuery = true)
    Page<Tours> findTourByDatePageable(String startDate, String endDate, Pageable pageable);

    @Query(value = "select * " +
            "from tours t " +
            "where t.unit_price >= ?1 and t.unit_price <= ?2 and date(t.start_time) >= ?3 and date(t.end_time) <= ?4", nativeQuery = true)
    Page<Tours> findTourByPriceAndDatePageable(Float minPrice, Float maxPrice, String startDate, String endDate, Pageable pageable);

    default Page<Tours> findTourByKoiNameAndByFarmName(Long koiId, Long farmId, Pageable pageable){
        Map<Long, Tours> uniqueToursMap = new HashMap<>();

        if (koiId != null) {
            List<Tours> toursListByKoi = findTourByKoiName(koiId);
            for (Tours tour : toursListByKoi) {
                uniqueToursMap.putIfAbsent(tour.getId(), tour); // Chỉ thêm nếu chưa tồn tại id trong map
            }
        }

        if (farmId != null) {
            List<Tours> toursListByFarm = findTourByFarmName(farmId);
            for (Tours tour : toursListByFarm) {
                uniqueToursMap.putIfAbsent(tour.getId(), tour); // Chỉ thêm nếu chưa tồn tại id trong map
            }
        }

        // Lấy các giá trị từ Map để tạo danh sách duy nhất
        List<Tours> uniqueTours = new ArrayList<>(uniqueToursMap.values());

        // Phân trang kết quả
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), uniqueTours.size());
        return new PageImpl<>(uniqueTours.subList(start, end), pageable, uniqueTours.size());
    }

    default Page<Tours> findTourByKoiNameAndByFarmNameWithUnitPrice(Long koiId, Long farmId, Float minPrice, Float maxPrice, Pageable pageable) {
        Map<Long, Tours> uniqueToursMap = new HashMap<>();

        List<Tours> toursByKoi = findTourByKoiNameWithPrice(koiId, minPrice, maxPrice);
        List<Tours> toursByFarm = findTourByFarmNameWithPrice(farmId, minPrice, maxPrice);

        for (Tours tour : toursByKoi) {
            uniqueToursMap.putIfAbsent(tour.getId(), tour);
        }

        for (Tours tour : toursByFarm) {
            uniqueToursMap.putIfAbsent(tour.getId(), tour);
        }

        // Lấy các giá trị duy nhất từ Map để tạo danh sách
        List<Tours> uniqueTours = new ArrayList<>(uniqueToursMap.values());

        // Phân trang kết quả
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), uniqueTours.size());
        return new PageImpl<>(uniqueTours.subList(start, end), pageable, uniqueTours.size());
    }

    default Page<Tours> findTourByKoiNameAndByFarmNameWithDate(Long koiId, Long farmId, String startDate, String endDate, Pageable pageable) {
        Map<Long, Tours> uniqueToursMap = new HashMap<>();

        List<Tours> toursByKoi = findTourByKoiNameWithDate(koiId, startDate, endDate);
        List<Tours> toursByFarm = findTourByFarmNameWithDate(farmId, startDate, endDate);

        for (Tours tour : toursByKoi) {
            uniqueToursMap.putIfAbsent(tour.getId(), tour);
        }

        for (Tours tour : toursByFarm) {
            uniqueToursMap.putIfAbsent(tour.getId(), tour);
        }

        // Lấy các giá trị duy nhất từ Map để tạo danh sách
        List<Tours> uniqueTours = new ArrayList<>(uniqueToursMap.values());

        // Phân trang kết quả
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), uniqueTours.size());
        return new PageImpl<>(uniqueTours.subList(start, end), pageable, uniqueTours.size());
    }

    default Page<Tours> findTourByKoiNameAndByFarmNameWithAllCondition(Long koiId, Long farmId, Float minPrice, Float maxPrice, String startDate, String endDate, Pageable pageable) {
        Map<Long, Tours> uniqueToursMap = new HashMap<>();

        List<Tours> toursByKoi = findTourByKoiNameWithAllCondition(koiId, minPrice, maxPrice, startDate, endDate);
        List<Tours> toursByFarm = findTourByFarmNameWithAllCondition(farmId, minPrice, maxPrice, startDate, endDate);

        for (Tours tour : toursByKoi) {
            uniqueToursMap.putIfAbsent(tour.getId(), tour);
        }

        for (Tours tour : toursByFarm) {
            uniqueToursMap.putIfAbsent(tour.getId(), tour);
        }

        // Lấy các giá trị duy nhất từ Map để tạo danh sách
        List<Tours> uniqueTours = new ArrayList<>(uniqueToursMap.values());

        // Phân trang kết quả
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), uniqueTours.size());
        return new PageImpl<>(uniqueTours.subList(start, end), pageable, uniqueTours.size());
    }


    default Page<Tours> findTourByUnitPriceAndDate(Float minPrice, Float maxPrice, String startDate, String endDate, Pageable pageable) {
        Map<Long, Tours> uniqueToursMap = new HashMap<>();

        // Thêm các tour theo giá vào Map, sử dụng id làm khóa để loại trừ trùng lặp
        List<Tours> toursByPrice = findTourByPrice(minPrice, maxPrice);
        for (Tours tour : toursByPrice) {
            uniqueToursMap.putIfAbsent(tour.getId(), tour);
        }

        // Thêm các tour theo ngày vào Map, tránh trùng lặp với các tour đã thêm
        List<Tours> toursByDate = findTourByDate(startDate, endDate);
        for (Tours tour : toursByDate) {
            uniqueToursMap.putIfAbsent(tour.getId(), tour);
        }

        // Lấy các giá trị duy nhất từ Map để tạo danh sách
        List<Tours> uniqueTours = new ArrayList<>(uniqueToursMap.values());

        // Phân trang kết quả
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), uniqueTours.size());
        return new PageImpl<>(uniqueTours.subList(start, end), pageable, uniqueTours.size());
    }

}
