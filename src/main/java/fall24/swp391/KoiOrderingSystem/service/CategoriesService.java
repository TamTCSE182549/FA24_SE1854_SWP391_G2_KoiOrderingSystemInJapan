package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Categories;
import fall24.swp391.KoiOrderingSystem.repo.ICategoriesRepository;

import java.util.List;

public class CategoriesService implements ICategoriesService {

    private ICategoriesRepository categoriesRepository;

    public void categoriesService(ICategoriesRepository categoriesRepo){
        categoriesRepository = categoriesRepo;
    }

    @Override
    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories findById(Long Id) {
        return null;
    }

    @Override
    public Categories save(Categories categories) {
        return categoriesRepository.save(categories);
    }

    @Override
    public void deletebyId(Long Id) {
        categoriesRepository.deleteById(Id);
    }
}
