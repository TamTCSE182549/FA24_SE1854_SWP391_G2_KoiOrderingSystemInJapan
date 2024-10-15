package fall24.swp391.KoiOrderingSystem.controller;

import fall24.swp391.KoiOrderingSystem.model.request.CategoryRequest;
import fall24.swp391.KoiOrderingSystem.model.response.CategoryResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Categories;
import fall24.swp391.KoiOrderingSystem.service.ICategoriesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/category")
@SecurityRequirement(name = "api")
public class CategoryController {
    @Autowired
    private ICategoriesService categoriesService;

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest){
        CategoryResponse categoryResponse = categoriesService.createCategory(categoryRequest);
        return new ResponseEntity<>("Create Koi's category: " + categoryRequest.getCategoryName() + " Success\n" + categoryResponse, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public  ResponseEntity<?> showCategory(){
        List<Categories> categories = categoriesService.findAll();
        if(categories.isEmpty()){
            return ResponseEntity.ok("Empty List");
        } else {
            return ResponseEntity.ok(categories);
        }
    }

    @PutMapping("/update/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        return categoriesService.updateCategory(categoryRequest, id);
    }

//    @PostMapping("/{categoryId}/kois/{koiId}")
//    public CategoryResponse addKoiToCategory(@PathVariable Long categoryId, @PathVariable Long koiId) {
//        return categoriesService.addKoiToCategory(categoryId, koiId);
//    }
//
//    @GetMapping("/Koi/Category/{id}")
//    public ResponseEntity<CategoryResponse> getCategoryWithKoi(@PathVariable Long id){
//        CategoryResponse categoryResponse = categoriesService.getCategoryWithKoiById(id);
//        return ResponseEntity.ok(categoryResponse);
//    }

}
