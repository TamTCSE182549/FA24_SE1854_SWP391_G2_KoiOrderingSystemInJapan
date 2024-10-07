package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Categories;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import fall24.swp391.KoiOrderingSystem.repo.ICategoriesRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoisRepository;

import java.util.List;

public class KoiService implements IKoisService{
    private IKoisRepository koisRepository;

    public void koisService(IKoisRepository koisRepo){
        koisRepository = koisRepo;
    }

    @Override
    public List<Kois> findAll() {
        return koisRepository.findAll();
    }

    @Override
    public Kois findById(Long Id) {
        return null;
    }

    @Override
    public Kois save(Kois kois) {
        return koisRepository.save(kois);
    }

    @Override
    public void deletebyId(Long Id) {
        koisRepository.deleteById(Id);
    }
}
