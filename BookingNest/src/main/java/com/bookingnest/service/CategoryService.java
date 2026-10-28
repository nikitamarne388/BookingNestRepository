package com.bookingnest.service;

import com.bookingnest.dto.request.CategoryRequest;
import com.bookingnest.dto.response.AdminCategoryResponse;

import java.util.List;

public interface CategoryService {

    List<AdminCategoryResponse> getAllCategories(Integer pageNumber, Integer pageSize, String sortOrder);

    AdminCategoryResponse getCategoryById(Long id);

    AdminCategoryResponse createCategory(CategoryRequest categoryRequest);

    AdminCategoryResponse updateCategory(Long id, CategoryRequest updateCategoryRequest);

    void deleteCategory(Long id);

    List<AdminCategoryResponse> getAllActiveChildrenCategoriesByParentId(Long parentId);

    List<AdminCategoryResponse> getAllCategoriesByIsActiveFlag( boolean isActive);

    List<AdminCategoryResponse> getAllActiveParentCategories();
}
