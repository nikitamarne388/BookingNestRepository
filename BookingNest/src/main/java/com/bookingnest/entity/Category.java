package com.bookingnest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categories", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_categories_name",
                columnNames = "name"
        ),
        @UniqueConstraint(
                name = "uk_categories_slug",
                columnNames = "slug"
        )
})
public class Category extends BaseEntity {

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 120)
    @Column(name = "slug", nullable = false, length = 120)
    private String slug;

    @Size(max = 500)
    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @URL
    @Size(max = 500)
    @Column(name = "icon_url", nullable = true, length = 500)
    private String iconUrl;

    @JoinColumn(name = "parent_id", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    public Category(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

}
