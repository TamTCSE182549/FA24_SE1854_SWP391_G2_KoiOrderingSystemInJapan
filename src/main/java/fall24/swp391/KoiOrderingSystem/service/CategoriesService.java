package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.exception.NotFoundEntity;
import fall24.swp391.KoiOrderingSystem.model.request.CategoryRequest;
import fall24.swp391.KoiOrderingSystem.model.response.CategoryResponse;
import fall24.swp391.KoiOrderingSystem.model.response.KoiResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Categories;
import fall24.swp391.KoiOrderingSystem.pojo.Kois;
import fall24.swp391.KoiOrderingSystem.repo.ICategoriesRepository;
import fall24.swp391.KoiOrderingSystem.repo.IKoisRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriesService implements ICategoriesService {

    @Autowired
    private ICategoriesRepository iCategoriesRepository;

    @Autowired
    private IKoisRepository iKoisRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Categories> findAll() {
        return iCategoriesRepository.findAll();
    }

    @Override
    public Optional<Categories> findById(Long id) {
        return iCategoriesRepository.findById(id);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Categories categories = modelMapper.map(categoryRequest, Categories.class);
        categories.setCateName(categoryRequest.getCategoryName());
        iCategoriesRepository.save(categories);
        return modelMapper.map(categories, CategoryResponse.class);
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest, Long id) {
        Categories categories = iCategoriesRepository.findCategoryById(id);
        if(categories == null){
            throw new NotFoundEntity("Category not found!");
        }
        categories.setCateName(categoryRequest.getCategoryName());
        categories.setDescription(categoryRequest.getDescription());
        iCategoriesRepository.save(categories);
        return modelMapper.map(categories, CategoryResponse.class);
    }

//    @Override
//    public CategoryResponse addKoiToCategory(Long categoryId, Long koiId) {
//        Categories category = iCategoriesRepository.findCategoryById(categoryId);
//        if (category == null) {
//            throw new NotFoundEntity("Category not found!");
//        }
//        Kois koi = iKoisRepository.findById(koiId).orElseThrow(() -> new NotFoundEntity("Koi not found!"));
//        category.getKois().add(koi);
//        iCategoriesRepository.save(category);
//        return modelMapper.map(category, CategoryResponse.class);
//    }
//
//    @Override
//    public CategoryResponse getCategoryWithKoiById(Long categoryId) {
//        Categories categories = iCategoriesRepository.findCategoryById(categoryId);
//        if(categories == null){
//            throw new NotFoundEntity("Not found Category!");
//        }
//        return convertToCategoryWithKoi(categories);
//    }
//
//    @Override
//    public CategoryResponse convertToCategoryWithKoi(Categories categories) {
//        CategoryResponse response = new CategoryResponse();
//        response.setCategoryId(categories.getId());
//        response.setCateName(categories.getCateName());
//        response.setDescription(categories.getDescription());
//
//        List<KoiResponse> koiResponses = categories.getKois().stream()
//                .map(kois -> {
//                    KoiResponse koiResponse = new KoiResponse();
//                    koiResponse.setId(kois.getId());
//                    return koiResponse;
//                }).collect(Collectors.toList());
//
//        response.setKois(koiResponses);
//        return response;
//    }

}
