package com.bookingnest;

import com.bookingnest.controller.admin.AdminCategoryController;
import com.bookingnest.dto.response.AdminCategoryResponse;
import com.bookingnest.service.CategoryService;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(AdminCategoryController.class)
public class AdminCategoryControllerTest {

    private static final String BASE_URL = "/admin/categories";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    // ------------------------------------------------------------------ POST /admin/categories

    private String badRequestBlankName = "{\n" +
            "  \"name\": \"\",\n" +
            "  \"description\": \"Towers and viewpoints with panoramic city views\",\n" +
            "  \"iconUrl\": \"https://placehold.co/256x256/png?text=observation\",\n" +
            "  \"parentId\": 2,\n" +
            "  \"displayOrder\": 15,\n" +
            "  \"active\": false\n" +
            "}";
    private String validRequestJson = "{\n" +
            "  \"name\": \"Boat Tours\",\n" +
            "  \"description\": \"Short sightseeing trips by boat\",\n" +
            "  \"iconUrl\": \"https://placehold.co/256x256/png?text=boatTours\",\n" +
            "  \"parentId\": 5,\n" +
            "  \"displayOrder\": 16,\n" +
            "  \"active\": true\n" +
            "}";
    private String badRequestInvalidIconURL = "{\n" +
            "  \"name\": \"Boat Tours\",\n" +
            "  \"description\": \"Short sightseeing trips by boat\",\n" +
            "  \"iconUrl\": \"https\",\n" +
            "  \"parentId\": 5,\n" +
            "  \"displayOrder\": 16,\n" +
            "  \"active\": true\n" +
            "}";

    private AdminCategoryResponse response(Long id, String name, String slug) {
        AdminCategoryResponse response = new AdminCategoryResponse();
        response.setId(id);
        response.setName(name);
        response.setSlug(slug);
        response.setDisplayOrder(1);
        response.setActive(true);
        return response;
    }
    
    @Test
    @DisplayName("POST : valid request returns 200 and the created category")
    void createCategory_ValidRequest_returnsCreatedCategory() throws Exception {
        when(categoryService.createCategory(any()))
                .thenReturn(response(19L, "Boat Tours", "boat-tours"));

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(validRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(19))
                .andExpect(jsonPath("$.name").value("Boat Tours"))
                .andExpect(jsonPath("$.slug").value("boat-tours"));
    }

    @Test
    @DisplayName("POST : invalid name returns 400 error code")
    void createCategory_invalidName_return400() throws Exception {

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestBlankName))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.statusMessage", containsString("Name field cannot be blank")))
                .andExpect(jsonPath("$.api").value(BASE_URL));

        verify(categoryService, never()).createCategory(any());
    }

    @Test
    @DisplayName("POST : invalid IconURL returns 400 Bad Request error")
    void createCategory_invalidURL_return400() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(badRequestInvalidIconURL))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.statusMessage", containsString("iconUrl: must be a valid URL")))
                .andExpect(jsonPath("$.api").value(BASE_URL));
    }

}
