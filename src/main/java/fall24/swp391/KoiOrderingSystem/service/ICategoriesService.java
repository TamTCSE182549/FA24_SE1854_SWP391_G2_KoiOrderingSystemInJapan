package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.CategoryRequest;
import fall24.swp391.KoiOrderingSystem.model.response.CategoryResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Categories;

import java.util.List;
import java.util.Optional;

public interface ICategoriesService {
    List<Categories> findAll();

    Optional<Categories> findById(Long id);

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(CategoryRequest categoryRequest, Long id);

//    CategoryResponse addKoiToCategory(Long categoryId, Long koiId);
//
//    CategoryResponse getCategoryWithKoiById(Long categoryId);
//
//    CategoryResponse convertToCategoryWithKoi(Categories categories);
}
