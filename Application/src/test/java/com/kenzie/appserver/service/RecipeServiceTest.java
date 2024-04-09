package com.kenzie.appserver.service;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;


import static org.mockito.Mockito.when;

public class RecipeServiceTest {

    private RecipeRepository recipeRepository;

    private RecipeService recipeService;

    private LambdaServiceClient lambdaServiceClient;

    @BeforeEach
    void setUp() {
        recipeRepository = mock(RecipeRepository.class);
        lambdaServiceClient = mock(LambdaServiceClient.class);
        recipeService = new RecipeService(recipeRepository, lambdaServiceClient);
    }

    @Test
    void testGetRecipeById() {
        // GIVEN
        String id = UUID.randomUUID().toString();
        RecipeRecord recipe = new RecipeRecord();
        recipe.setRecipeId(id);
        recipe.setTitle("Recipe 1");

        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));

        // WHEN
        Optional<RecipeRecord> result = recipeService.getRecipeById(id);

        // THEN
        assertNotNull(result);
        assertEquals(recipe.getTitle(), result.get().getTitle());
    }

    @Test
    void testCreateRecipe() {
        // GIVEN
        RecipeRecord recipe = new RecipeRecord();
        recipe.setTitle("Recipe 1");

        when(recipeRepository.save(any())).thenReturn(recipe);

        // WHEN
        RecipeRecord result = recipeService.createRecipe(recipe);

        // THEN
        assertNotNull(result);
        assertEquals(recipe.getTitle(), result.getTitle());
    }

    @Test
    void testUpdateRecipe() {
        // GIVEN
        String id = UUID.randomUUID().toString();
        RecipeRecord recipe = new RecipeRecord();
        recipe.setRecipeId(id);
        recipe.setTitle("Recipe 1");

        when(recipeRepository.existsById(id)).thenReturn(true);
        when(recipeRepository.save(any())).thenReturn(recipe);

        // WHEN
        recipeService.updateRecipe(id, recipe);

        // THEN
        verify(recipeRepository, times(1)).save(recipe);
    }

    @Test
    void testDeleteRecipe() {
        // GIVEN
        String id = UUID.randomUUID().toString();

        // WHEN
        recipeService.deleteRecipe(id);

        // THEN
        verify(recipeRepository, times(1)).deleteById(id);
    }
}
