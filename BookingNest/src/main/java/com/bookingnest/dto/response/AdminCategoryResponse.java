package com.bookingnest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Author:Nikita Parkhande
 *
 * This class contains the categoryResponse fields to be returned in the response
 * AdminCategoryResponse  (id, name, slug, description, iconUrl, parentId, parentName,displayOrder, active, createdAt, updatedAt)
 *
 */
@Getter
@NoArgsConstructor
@Data
public class AdminCategoryResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String iconUrl;
    private Long parentId;
    private String parentName;
    private Integer displayOrder;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;

}
