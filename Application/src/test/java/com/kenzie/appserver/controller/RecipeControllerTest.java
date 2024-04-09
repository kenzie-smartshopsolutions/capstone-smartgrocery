package com.kenzie.appserver.controller;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
public class RecipeControllerTest {

    private RecipeService recipeService;
    private RecipeController recipeController;

    @BeforeEach
    void setUp() {
        recipeService = mock(RecipeService.class);
        recipeController = new RecipeController(recipeService);
    }

    @Test
    void testGetAllRecipes() {
        // GIVEN
        List<RecipeRecord> recipeList = List.of(new RecipeRecord(), new RecipeRecord());

        when(recipeService.getAllRecipes()).thenReturn(recipeList);

        // WHEN
        List<RecipeRecord> result = recipeController.getAllRecipes();

        // THEN
        assertNotNull(result);
        assertEquals(recipeList.size(), result.size());
    }

    @Test
    void testGetRecipeById() {
        // GIVEN
        String id = "someId";
        RecipeRecord recipe = new RecipeRecord();
        recipe.setRecipeId(id);

        when(recipeService.getRecipeById(id)).thenReturn(Optional.of(recipe));

        // WHEN
        Optional<RecipeRecord> result = recipeController.getRecipeById(id);

        // THEN
        assertNotNull(result);
        assertEquals(recipe.getRecipeId(), result.get().getRecipeId());
    }

    @Test
    void testCreateRecipe() {
        // GIVEN
        RecipeRecord recipe = new RecipeRecord();

        when(recipeService.createRecipe(recipe)).thenReturn(recipe);

        // WHEN
        RecipeRecord result = recipeController.createRecipe(recipe);

        // THEN
        assertNotNull(result);
        assertEquals(recipe, result);
    }

    @Test
    void testUpdateRecipe() {
        // GIVEN
        String id = "someId";
        RecipeRecord recipe = new RecipeRecord();
        recipe.setRecipeId(id);

        // WHEN
        recipeController.updateRecipe(id, recipe);

        // THEN
        verify(recipeService, times(1)).updateRecipe(id, recipe);
    }

    @Test
    void testDeleteRecipe() {
        // GIVEN
        String id = "someId";

        // WHEN
        recipeController.deleteRecipe(id);

        // THEN
        verify(recipeService, times(1)).deleteRecipe(id);
    }
}
