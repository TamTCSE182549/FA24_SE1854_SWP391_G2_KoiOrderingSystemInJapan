package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Checkin;

import java.util.List;

public interface ICheckinService {

    List<Checkin> findAll();

    Checkin findById(Long Id);

    Checkin save(Checkin checkin);

    void deletebyId(Long theId);
}
