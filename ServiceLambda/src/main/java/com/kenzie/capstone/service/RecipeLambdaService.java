package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.RecipeDao;
import com.kenzie.capstone.service.model.RecipeData;
import com.kenzie.capstone.service.model.RecipeRecord;

import com.kenzie.capstone.service.model.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;


public class RecipeLambdaService {

    private static RecipeDao recipeDao;
    private static final Logger log = LoggerFactory.getLogger(RecipeLambdaService.class);
    @Inject
    public RecipeLambdaService(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }

    /**
     * Retrieves recipe data for a given recipe ID.
     *
     * @param recipeId ID of the recipe to retrieve
     * @return RecipeData object containing recipe details
     * @throws RuntimeException if the recipe is not found
     */
    public RecipeData getRecipeData(String recipeId) {
        try {
            RecipeRecord recipeRecord = recipeDao.getRecipeData(recipeId);

            // Handle recipe not found scenario
            if (recipeRecord == null) {
                log.error("Recipe not found with ID: {}",recipeId);
                throw new RuntimeException("Recipe not found");
            }
            return recipeDao.convertToRecipeData(recipeRecord);
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

    public  RecipeData setRecipeData(RecipeData recipeData) {
            String recipeId = recipeData.getRecipeId();
            RecipeRecord recipeRecord = recipeDao.setRecipeData(recipeId, recipeData);
            return recipeDao.convertToRecipeData(recipeRecord);
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