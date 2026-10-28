package com.bookingnest.dto.response;

import com.bookingnest.entity.Category;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Author:Nikita Parkhande
 *
 * This class contains the categoryResponse fields to be returned in the response
 *  CategoryResponse       (id, name, slug, description, iconUrl, parentId, displayOrder)
 *
 */

@Getter
@NoArgsConstructor
@Data
public class CategoryResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String iconUrl;
    private Long parentId;
    private Integer displayOrder;
}
