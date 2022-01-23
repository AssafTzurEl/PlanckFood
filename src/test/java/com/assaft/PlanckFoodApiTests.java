package com.assaft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlanckFoodApiTests {

    @BeforeEach
    public void beforeEachTest() {
        repository.deleteAll();
    }

    @Test
    public void givenNoFoods_whenGetAll_shouldReturnEmptyList() throws Exception {

        mockMvc.perform(get(FoodController.BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void whenAddFood_shouldReturnCreatedAndFoodObject() throws Exception {

        addFood(FoodTestObjects.MAIN_COURSE_1)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(FoodTestObjects.MAIN_COURSE_1.getName()))
                .andExpect(jsonPath("$.type").value(FoodTestObjects.MAIN_COURSE_1.getType().toString()))
                .andExpect(jsonPath("$.rating").value(5L))
                .andExpect(header().string("location", startsWith(FoodController.BASE_PATH + "/")));
    }

    @Test
    public void givenFoodItem_whenDeleteItem_shouldBeDeleted() throws Exception {

        // Given food item:
        ResultActions resultActions = addFood(FoodTestObjects.MAIN_COURSE_1)
                .andExpect(status().isCreated());

        String json = resultActions.andReturn().getResponse().getContentAsString();
        Food createdFood = toFood(json);

        // When delete item:
        mockMvc.perform(delete(pathWithId(createdFood.getId())))
                .andExpect(status().isOk());

        // Should be deleted:
        mockMvc.perform(get(pathWithId(createdFood.getId())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenNoFoodItem_whenDeleteItem_shouldReturnNotFound() throws Exception {

        // When delete non-existent item:
        mockMvc.perform(delete(pathWithId(Long.MAX_VALUE)))
        // Should return 404:
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenFoodItems_whenDeleteAll_shouldBeEmpty() throws Exception {

        // Given food items:
        addFood(FoodTestObjects.MAIN_COURSE_1)
                .andExpect(status().isCreated());
        addFood(FoodTestObjects.DESSERT_1)
                .andExpect(status().isCreated());

        // When delete all:
        mockMvc.perform(delete(FoodController.BASE_PATH))
                .andExpect(status().isOk());

        // Should be empty:
        mockMvc.perform(get(FoodController.BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void givenNoFoodItems_whenDeleteAll_shouldBeEmpty() throws Exception {

        // When delete all:
        mockMvc.perform(delete(FoodController.BASE_PATH))
                .andExpect(status().isOk());

        // Should be empty:
        mockMvc.perform(get(FoodController.BASE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    // TODO: Add tests for all APIs and use cases

    private static String toJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    private static Food toFood(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, Food.class);
    }

    private static String pathWithId(Long id) {
        return String.format("%s/%d", FoodController.BASE_PATH, id);
    }

    private ResultActions addFood(Food food) throws Exception {
        return mockMvc.perform(post(FoodController.BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(food)));
    }

    @Autowired
    private MockMvc mockMvc;

    // Force test context to use this repository so that we can reset it before each test:
    @Autowired
    private FoodRepository repository;
}
