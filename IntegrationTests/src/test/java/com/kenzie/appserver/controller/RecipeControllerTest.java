package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.RecipeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import java.util.Collections;
import java.util.Optional;

@IntegrationTest
public class RecipeControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private RecipeService recipeService;
    @InjectMocks
    private RecipeController recipeController;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test method for retrieving all recipes
    @Test
    void getAllRecipes_ReturnsListOfRecipes() throws Exception {
        // Mocking the service to return a list of recipes
        RecipeRecord recipe1 = new RecipeRecord("1", "Recipe 1", Arrays.asList("Ingredient 1", "Ingredient 2"), "Instructions 1");
        RecipeRecord recipe2 = new RecipeRecord("2", "Recipe 2", Arrays.asList("Ingredient 3", "Ingredient 4"), "Instructions 2");
        when(recipeService.getAllRecipes()).thenReturn(Arrays.asList(recipe1, recipe2));

        // Performing the request and verifying the response
        mvc.perform(get("/recipes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Recipe 1"))
                .andExpect(jsonPath("$[1].title").value("Recipe 2"));
    }

    //Test method for retrieving a recipe by ID with a non-existent ID
    @Test
    void getRecipeById_NonExistentId_ReturnsNotFound() throws Exception {
        // Mocking the service to return an empty optional for a non-existent recipe ID
        when(recipeService.getRecipeById("nonexistentId")).thenReturn(Optional.empty());

        // Performing the request and verifying the response
        mvc.perform(get("/recipes/nonexistentId")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    //Test method for retrieving a recipe by ID with an existing ID
    @Test
    void getRecipeById_WithExistingId_ReturnsRecipe() throws Exception {
        // Mocking the service to return a recipe by ID
        RecipeRecord recipe = new RecipeRecord("1", "Recipe 1", Arrays.asList("Ingredient 1", "Ingredient 2"), "Instructions 1");
        when(recipeService.getRecipeById("1")).thenReturn(Optional.of(recipe));

        // Performing the request and verifying the response
        mvc.perform(get("/recipes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Recipe 1"));
    }
    // Test retrieving all recipes when the database is empty
    @Test
    void getAllRecipes_WhenDatabaseIsEmpty_ReturnsEmptyList() throws Exception {
        // Mocking the service to return an empty list of recipes
        when(recipeService.getAllRecipes()).thenReturn(Collections.emptyList());

        // Performing the request and verifying the response
        mvc.perform(get("/recipes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
    //Test method for creating a new recipe with valid data
    @Test
    void createRecipe_WithValidData_ReturnsCreatedRecipe() throws Exception {
        // Creating a new recipe
        RecipeRecord newRecipe = new RecipeRecord("3", "New Recipe", Arrays.asList("Ingredient 1", "Ingredient 2"), "Instructions");
        when(recipeService.createRecipe(any(RecipeRecord.class))).thenReturn(newRecipe);

        // Performing the request and verifying the response
        mvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newRecipe))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Recipe"));
    }
    //Test method for creating a new recipe with invalid data
    @Test
    void createRecipe_InvalidData_ReturnsBadRequest() throws Exception {
        // Creating a new recipe with invalid data (missing title)
        RecipeRecord invalidRecipe = new RecipeRecord("3", null, Arrays.asList("Ingredient 1", "Ingredient 2"), "Instructions");

        // Performing the request and verifying the response
        mvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalidRecipe))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    // Test creating a new recipe with an empty list of ingredients
    @Test
    void createRecipe_WithEmptyIngredients_ReturnsBadRequest() throws Exception {
        // Creating a new recipe with an empty list of ingredients
        RecipeRecord emptyIngredientsRecipe = new RecipeRecord("5", "Empty Ingredients Recipe", Collections.emptyList(), "Instructions");

        // Performing the request and verifying the response
        mvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(emptyIngredientsRecipe))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    //Test method for updating an existing recipe with an existing ID
    @Test
    void updateRecipe_WithExistingId_ReturnsUpdatedRecipe() throws Exception {
        // Mocking the behavior of the recipeService.updateRecipe method
        doNothing().when(recipeService).updateRecipe(eq("1"), any(RecipeRecord.class));

        // Performing the PUT request and verifying the response
        mvc.perform(put("/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Recipe\",\"ingredients\":[\"Updated Ingredient 1\",\"Updated Ingredient 2\"],\"instructions\":\"Updated Instructions\"}"))
                .andExpect(status().isOk());
    }
    //Test method for updating an existing recipe with a non-existent ID
    @Test
    void updateRecipe_WithNonExistentId_ReturnsNotFound() throws Exception {
        // Mocking the behavior of the recipeService.updateRecipe method for a non-existent ID
        doThrow(Exception.class).when(recipeService).updateRecipe(eq("nonexistentId"), any(RecipeRecord.class));

        // Performing the PUT request and verifying the response
        mvc.perform(put("/recipes/nonexistentId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Recipe\",\"ingredients\":[\"Updated Ingredient 1\",\"Updated Ingredient 2\"],\"instructions\":\"Updated Instructions\"}"))
                .andExpect(status().isNotFound());
    }
    // Test method for deleting an existing recipe with an existing ID
    @Test
    void deleteRecipe_WithExistingId() throws Exception {
        // Deleting an existing recipe
        mvc.perform(delete("/recipes/1"))
                .andExpect(status().isOk());
    }
    // Test method for deleting a non-existent recipe with a non-existent ID
    @Test
    void deleteRecipe_NonExistentId() throws Exception {
        doNothing().when(recipeService).deleteRecipe("nonexistentId");
        // Performing the DELETE request and verifying the response
        mvc.perform(delete("/recipes/nonexistentId"))
                .andExpect(status().isNotFound());
    }
}
