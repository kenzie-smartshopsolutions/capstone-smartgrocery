package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.PantryRepository;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Ingredient;
import com.kenzie.appserver.service.model.Pantry;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class PantryService {

    private PantryRepository pantryRepository;

    private LambdaServiceClient lambdaServiceClient;
    private RecipeRepository recipeRepository;

    public PantryService(PantryRepository pantryRepository, LambdaServiceClient lambdaServiceClient, RecipeRepository recipeRepository) {
        this.pantryRepository = pantryRepository;
        this.lambdaServiceClient = lambdaServiceClient;
        this.recipeRepository = recipeRepository;
    }

    public PantryRecord getPantry(String userId) {
        return pantryRepository.findById(userId);
    }
    // Retrieve pantry items for a user or household
    public List<PantryRecord> getPantryItems(String userId) {
        return pantryRepository.findByUserId(userId);
    }

    // Add a new pantry item
    public PantryRecord addPantryItem(PantryRecord pantryRecord) {
        return pantryRepository.save(pantryRecord);
    }

    // Update an existing pantry item
    public PantryRecord updatePantryItem(PantryRecord pantryRecord) {
        return pantryRepository.save(pantryRecord);
    }

    // Delete a pantry item by ID
    public void deletePantryItem(String pantryItemId) {
        pantryRepository.deleteById(pantryItemId);
    }

    public void updatePantryFromRecipe (String userId, String recipeId) {

        List<PantryRecord> pantry = pantryRepository.findByUserId(userId);
        //need to get pantry record to be able to set quantity...
//        Optional<PantryRecord> pantry = pantryRepository.findById(userId);
        //same with recipe
        RecipeRecord recipe = recipeRepository.findByRecipeId(recipeId);

        //getIngredients returns list, how do I get individual ingredients?
        //how do I get recipe ingredient quantities?

        // check if pantry has ingredient
//        if (pantry.contains(recipe.getIngredients())) {
        // validation -  if pantryItem != null
        if (pantry != null && recipe != null) {
            for (PantryRecord record : pantry) {
                for (Ingredient ingredient : recipe.getIngredients()) {
                    if (record.getItemName().equalsIgnoreCase(ingredient.getIngredientName())) {
                        //subtract amount used in recipe from pantry
                        //update pantry with new amount
                        record.setQuantity(record.getQuantity() - ingredient.getQuantity());
                        //      pantry.setQuantity(pantry.getQuantity() - ingredient.getQuantity());
                        //can't setQuanity in pantry because its a list
                    }
                }
            }
            // ** would need some sort of validation if pantry items < 0
            // maybe if pantry item <= 0, set pantry item to 0
            // (there have been times where I just bought stuff from the store just to
            // make something, if i didnt have all ingredients)
            //.save(pantry)
        }
    }
}
