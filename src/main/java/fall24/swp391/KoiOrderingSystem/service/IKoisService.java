package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Kois;

import java.util.List;

public interface IKoisService {
    List<Kois> findAll();

    Kois findById(Long Id);

    Kois save(Kois kois);

    void deletebyId(Long Id);
}
