package com.bookingnest.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

/**
 * Author:Nikita Parkhande
 *
 * This class contains the categoryRequest fields to be accepted in the request
 * CategoryRequest        (name, description, iconUrl, parentId, displayOrder, active)
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryRequest {

    @Size(max = 100, message = "Maximum size allowed for name field is 100 characters")
    @NotBlank(message = "Name field cannot be blank")
    private String name;

    @Size(min = 3, max = 500, message = "Length of description allowed is Minimum 3 and Maximum 500 characters.")
    private String description;

    @Size(max = 500, message = "Length of URL allowed is Maximum 500 characters.")
    @URL
    private String iconUrl;

    private Long parentId;

    private Integer displayOrder;

    private Boolean active;
}
