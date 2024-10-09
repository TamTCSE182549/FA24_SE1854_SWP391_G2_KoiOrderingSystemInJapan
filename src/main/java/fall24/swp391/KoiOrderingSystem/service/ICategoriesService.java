package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.pojo.Categories;

import java.util.List;

public interface ICategoriesService {
    List<Categories> findAll();

    Categories findById(Long Id);

    Categories save(Categories categories);

    void deletebyId(Long Id);
}
