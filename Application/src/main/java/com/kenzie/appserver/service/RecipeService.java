package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Example;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.ExampleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    private RecipeRepository recipeRepository;
    private LambdaServiceClient lambdaServiceClient;

    public RecipeService(RecipeRepository recipeRepository, LambdaServiceClient lambdaServiceClient) {
        this.recipeRepository = recipeRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    public List<RecipeRecord> getAllRecipes() {
        return (List<RecipeRecord>) recipeRepository.findAll();
    }
    public Optional<RecipeRecord> getRecipeById(String id) {
        return recipeRepository.findById(id);
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
        return recipeRepository.save(recipeRecord);
    }

    public void updateRecipe(String id, RecipeRecord recipeRecord) {
        recipeRecord.setRecipeId(id);
        recipeRepository.save(recipeRecord);
    }

    public void deleteRecipe(String id) {
        recipeRepository.deleteById(id);
    }
}
