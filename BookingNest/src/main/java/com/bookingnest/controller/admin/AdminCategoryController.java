package com.bookingnest.controller.admin;

import com.bookingnest.dto.request.CategoryRequest;
import com.bookingnest.dto.response.AdminCategoryResponse;
import com.bookingnest.dto.response.CategoryResponse;
import com.bookingnest.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<AdminCategoryResponse>> getAllCategories(@RequestParam Integer pageNumber,
                                                                        @RequestParam Integer pageSize,
                                                                        @RequestParam String sortBy)
    {
        return ResponseEntity.ok(categoryService.getAllCategories(pageNumber,pageSize,sortBy));
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<AdminCategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping("/categories")
    public ResponseEntity<AdminCategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(categoryService.createCategory(categoryRequest));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<AdminCategoryResponse> updateCategory(@PathVariable Long id , @Valid @RequestBody CategoryRequest updateCategoryRequest) {
        return ResponseEntity.ok(categoryService.updateCategory(id,updateCategoryRequest));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories/active/{isActive}")
    public ResponseEntity<List<AdminCategoryResponse>> getAllCategoriesByIsActiveFlag(@PathVariable Boolean isActive) {
        return ResponseEntity.ok(categoryService.getAllCategoriesByIsActiveFlag(isActive));
    }

    @GetMapping("/categories/parent")
    public ResponseEntity<List<AdminCategoryResponse>> getAllActiveParentCategories() {
        return ResponseEntity.ok(categoryService.getAllActiveParentCategories());
    }

    @GetMapping("/categories/child/{parentId}")
    public ResponseEntity<List<AdminCategoryResponse>> getAllActiveChildrenCategoriesByParentId(@PathVariable Long parentId) {
        return ResponseEntity.ok(categoryService.getAllActiveChildrenCategoriesByParentId(parentId));
    }
}
