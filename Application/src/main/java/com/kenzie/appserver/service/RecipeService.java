package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.RecipeData;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Service
public class RecipeService {
    private RecipeRepository recipeRepository;
    private LambdaServiceClient lambdaServiceClient;

    public RecipeService(RecipeRepository recipeRepository, LambdaServiceClient lambdaServiceClient) {
        this.recipeRepository = recipeRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    /*When the method with @Cacheable("recipe") is called,
    the program first checks if it has done the exact same thing before (like fetching all recipes from the database)
    and stored the result in a cache labeled "recipe." If it finds the result there, it quickly retrieves it from the cache.
     If not, it goes ahead and does the usual work, but also saves the result in the cache for future use.
      So,  the cache named "recipe" acts like a storage place for the results of this specific method
     */
    @Cacheable("Recipe")
    public List<RecipeRecord> getAllRecipes() {
        return (List<RecipeRecord>) recipeRepository.findAll();
    }

    public Optional<RecipeRecord> getRecipeById(String recipeId) {
        // Attempt to fetch recipe data from the local repository
        Optional<RecipeRecord> localRecipe = recipeRepository.findById(recipeId);

        // If recipe data is found in the local repository, return it
        if (localRecipe.isPresent()) {
            return localRecipe;
        }

        // If recipe data is not found in the local repository, fetch it from the Lambda service
        RecipeData recipeDataFromLambda = lambdaServiceClient.getRecipe(recipeId);

        // If recipe data is found in the Lambda service, construct a RecipeRecord from it and return
        if (recipeDataFromLambda != null) {
            RecipeRecord recipeRecordFromLambda = new RecipeRecord();
            recipeRecordFromLambda.setRecipeId(recipeDataFromLambda.getRecipeId());
            recipeRecordFromLambda.setTitle(recipeDataFromLambda.getTitle());
            recipeRecordFromLambda.setInstructions(recipeDataFromLambda.getInstructions());
            recipeRecordFromLambda.setIngredients(recipeDataFromLambda.getIngredients());

            return Optional.of(recipeRecordFromLambda);
        }
        // If recipe data is not found in either the local repository or the Lambda service, return empty Optional
        return Optional.empty();
    }
    public RecipeRecord createRecipe(RecipeRecord recipeRecord) {
    // Check if the provided recipeRecord contains a recipeId
        String recipeId = recipeRecord.getRecipeId();

        // Generate a new recipeId if it's not provided or not found
        if (recipeId == null || recipeId.trim().isEmpty() || !recipeRepository.existsById(recipeId)) {
            recipeId = UUID.randomUUID().toString();
            recipeRecord.setRecipeId(recipeId);
        } else {
            // Throw an exception if the provided recipeId already exists
            throw new IllegalArgumentException("Recipe with ID " + recipeId + " already exists.");
        }
        //return recipeRepository.save(recipeRecord);
        // Save recipe data to the local repository
        RecipeRecord savedRecipe = recipeRepository.save(recipeRecord);

        //interacting with LambdaServiceClient to send recipe data
        RecipeData recipeData = new RecipeData();
        recipeData.setRecipeId(savedRecipe.getRecipeId());
        recipeData.setTitle(savedRecipe.getTitle());
        recipeData.setInstructions(savedRecipe.getInstructions());
        recipeData.setIngredients(savedRecipe.getIngredients());
        lambdaServiceClient.setRecipe(recipeData);

        return savedRecipe;
    }

    public void updateRecipe(String recipeId, RecipeRecord recipeRecord) {
        recipeRecord.setRecipeId(recipeId);
        // Update recipe data in the local repository
        recipeRepository.save(recipeRecord);
    }

    public void deleteRecipe(String recipeId) {

        recipeRepository.deleteById(recipeId);

        // interacting with LambdaServiceClient to delete recipe data
       // lambdaServiceClient.deleteRecipe(recipeId);
    }
}
/*
Using Lambda here makes sure that if the recipe you're looking for isn't found in the usual place
 (the local storage), the app can look for it in another place (the Lambda service).
  This helps the app to always find the recipe you want, even if it's not stored on the device.
It's like having a backup plan in case the first option doesn't work
 */