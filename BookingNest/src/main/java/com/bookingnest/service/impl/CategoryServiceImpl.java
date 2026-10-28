package com.bookingnest.service.impl;

import com.bookingnest.dto.request.CategoryRequest;
import com.bookingnest.dto.response.AdminCategoryResponse;
import com.bookingnest.entity.Category;
import com.bookingnest.exception.BusinessRuleException;
import com.bookingnest.exception.DuplicateResourceException;
import com.bookingnest.exception.ResourceNotFoundException;
import com.bookingnest.helper.SlugGenerator;
import com.bookingnest.mapper.CategoryMapper;
import com.bookingnest.repository.CategoryRepository;
import com.bookingnest.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final SlugGenerator slugGenerator;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, SlugGenerator slugGenerator) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.slugGenerator = slugGenerator;
    }

    @Override
    public List<AdminCategoryResponse> getAllCategories(Integer pageNumber, Integer pageSize, String sortBy) {
        Sort sort = Sort.by(sortBy).ascending();
        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categoryMapper.toAdminResponseList(categories.getContent());
    }

    @Override
    public AdminCategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
        return categoryMapper.toAdminResponse(category);
    }

    @Override
    public AdminCategoryResponse createCategory(CategoryRequest categoryRequest) {

        if(categoryRequest.getName() != null && categoryRepository.existsByNameIgnoreCase(categoryRequest.getName().trim())){
            throw new DuplicateResourceException("Category", "name", categoryRequest.getName());
        }

        Category category = categoryMapper.toEntity(categoryRequest);

        //add slug details
        String slug = slugGenerator.generate(category.getName());
        if(categoryRepository.existsBySlug(slug)){
            throw new DuplicateResourceException("Category", "slug", slug);
        }else{
            category.setSlug(slug);
        }

        //add parent details
        if(categoryRequest.getParentId() != null) {
            category.setParent(resolveParentCategory(categoryRequest.getParentId(), category.getId()));
        }
        return categoryMapper.toAdminResponse(categoryRepository.save(category));
    }

    private Category resolveParentCategory(Long parentId, Long currentId) {

        if(Objects.equals(currentId, parentId)) {
            throw new BusinessRuleException("A category cannot be its own parent");
        }

        Category asParentCategory = categoryRepository.findById(parentId).orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + parentId));

        if(asParentCategory.getParent() != null){
            throw new BusinessRuleException("The parent category id given is a child category. Hence, cannot be added as a parent");
        }

        if(currentId != null && categoryRepository.existsByParentId(currentId)) {
            throw new BusinessRuleException("The category that has children cannot be a child itself");
        }

        return asParentCategory;
    }

    @Override
    public AdminCategoryResponse updateCategory(Long id, CategoryRequest updateCategoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));

        if(updateCategoryRequest.getName() != null && categoryRepository.existsByNameIgnoreCaseAndIdNot(updateCategoryRequest.getName(), id)) {
            throw new DuplicateResourceException("Category", "name" , updateCategoryRequest.getName());
        }

        categoryMapper.updateEntity(updateCategoryRequest, category);

        //add slug details
        String slug = slugGenerator.generate(category.getName());
        if(categoryRepository.existsBySlugAndIdNot(slug, id)) {
            throw new DuplicateResourceException("Category", "slug", slug);
        }else {
            category.setSlug(slug);
        }

        //add parent details
        if(updateCategoryRequest.getParentId() != null) {
            category.setParent(resolveParentCategory(updateCategoryRequest.getParentId(), category.getId()));
        }

        return categoryMapper.toAdminResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        if(categoryRepository.existsByParentId(id)) {
            throw new BusinessRuleException("Category cannot be deleted as its still a parent of other child categories. Please delete all its child categories first");
        }

        Category categoryToBeDeleted = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
        categoryRepository.delete(categoryToBeDeleted);
    }

    @Override
    public List<AdminCategoryResponse> getAllActiveChildrenCategoriesByParentId(Long parentId) {
        if(parentId == null) {
            throw new BusinessRuleException("There are no children's available with null parentId");
        }

        List<Category> activeChildCategories = categoryRepository.findByParentIdAndActiveTrueOrderByDisplayOrderAsc(parentId);

        return categoryMapper.toAdminResponseList(activeChildCategories);
    }

    @Override
    public List<AdminCategoryResponse> getAllCategoriesByIsActiveFlag(boolean isActive) {
        List<Category> categories = isActive ? categoryRepository.findByActiveTrue() : categoryRepository.findByActiveFalse();
        return categoryMapper.toAdminResponseList(categories);
    }

    @Override
    public List<AdminCategoryResponse> getAllActiveParentCategories() {
        List<Category> activeParentCategories = categoryRepository.findByActiveTrueAndParentIdIsNullOrderByDisplayOrderAsc();
        return categoryMapper.toAdminResponseList(activeParentCategories);
    }
}
