package com.bookingnest.repository;

import com.bookingnest.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /** Create : is the name already taken (case-insensitive) */
    boolean existsByNameIgnoreCase(String name);

    boolean existsBySlug(String slug);

    /** Update : is the name already existing other than the current one */
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    boolean existsBySlugAndIdNot(String slug, Long id);

    /** delete : check if the id is a parent id */
    boolean existsByParentId(Long Id);

    /** select : Filter queries */

    List<Category> findByActiveTrue();

    List<Category> findByActiveFalse();

    //get all active child categories
    List<Category> findByParentIdAndActiveTrueOrderByDisplayOrderAsc(Long Id);

    //get all active parent categories
    List<Category> findByActiveTrueAndParentIdIsNullOrderByDisplayOrderAsc();
}
