package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.RecipeDao;
import com.kenzie.capstone.service.model.RecipeData;
import com.kenzie.capstone.service.model.RecipeRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

//needs modification
public class RecipeLambdaService {

    private RecipeDao recipeDao;
    private static final Logger log = LoggerFactory.getLogger(RecipeLambdaService.class);
    @Inject
    public RecipeLambdaService(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }


    //needs modification
    /**
     * Retrieves recipe data for a given recipe ID.
     *
     * @param recipeId ID of the recipe to retrieve
     * @return RecipeData object containing recipe details
     * @throws RuntimeException if the recipe is not found
     */
    public RecipeData getRecipeData(String recipeId) {
        try {
            return recipeDao.getRecipeData(recipeId);
        } catch (Exception e) {
            log.error("Error fetching recipe data for ID: {}", recipeId, e);
            throw new RuntimeException("Error fetching recipe data", e);
        }
    }

    /**
     * Sets recipe data in the database.
     *
     * @param recipeData The recipe data to set.
     * @return The set recipe data.
     */

    //????
    public static RecipeData setRecipeData(RecipeData recipeData) {
        try {
        // Call the DAO method to set recipe data
        RecipeRecord recipeRecord = recipeDao.setRecipeData(recipeData.getRecipeId(), recipeData);

        // Construct and return RecipeData object using the data from the saved RecipeRecord
        RecipeData savedRecipeData = new RecipeData();
        savedRecipeData.setRecipeId(recipeRecord.getRecipeId());
        savedRecipeData.setRecipeName(recipeRecord.getTitle());
        savedRecipeData.setInstruction(recipeRecord.getInstructions());
        savedRecipeData.setIngredients(recipeRecord.getIngredients());

        return savedRecipeData;

        } catch (Exception e) {
            log.error("Error setting recipe data: {}", e.getMessage(), e);
            throw new RuntimeException("Error setting recipe data", e);
        }

    }

    /**
     * Deletes recipe data for a given recipe ID.
     *
     * @param recipeId ID of the recipe to delete
     */
    public void deleteRecipeData(String recipeId) {
        try {
            // Call the DAO method to delete recipe data
            recipeDao.deleteRecipeRecord(recipeId);
        } catch (Exception e) {
            log.error("Error deleting recipe data for ID: {}, error: {}", recipeId, e.getMessage());
            throw e;
        }
    }

    /**
     * Updates existing recipe data.
     *
     * @param recipeData Updated RecipeData object
     * @return Updated RecipeData object
     */
    public RecipeData updateRecipeData(RecipeData recipeData) {
        try {
            // Call the DAO method to update recipe data
            return recipeDao.updateRecipeData(recipeData);
        } catch (Exception e) {
            log.error("Error updating recipe data for ID: {}, error: {}", recipeData.getRecipeId(), e.getMessage());
            throw e;
        }
    }
}