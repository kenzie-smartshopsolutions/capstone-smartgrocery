package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.PantryRepository;

import com.kenzie.appserver.repositories.RecipeRepository;

import com.kenzie.appserver.repositories.model.FoodCategoryConverter;

import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.model.Ingredient;
import com.kenzie.appserver.service.model.Pantry;
import com.kenzie.capstone.service.client.LambdaServiceClient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.util.Optional;

import java.util.Map;


@Service
public class PantryService {

    @Autowired
    private PantryRepository pantryRepository;

    private Map<String, String> foodCategories;

    private LambdaServiceClient lambdaServiceClient;

    private RecipeRepository recipeRepository;


    @PostConstruct // Execute after Bean initialization
    public void loadFoodCategories() {
        File file = new File("/food_category.csv"); // Replace with the correct path
        try {
            foodCategories = FoodCategoryConverter.convertCsvToCategoryMap();
        } catch (IOException e) {
            // Handle error, maybe log a message
            e.printStackTrace();
        }
    }


    public PantryService(PantryRepository pantryRepository, LambdaServiceClient lambdaServiceClient, RecipeRepository recipeRepository) {
        this.pantryRepository = pantryRepository;
        this.lambdaServiceClient = lambdaServiceClient;
        this.recipeRepository = recipeRepository;
    }

    public Optional<PantryRecord> getPantry(String userId) {
        return pantryRepository.findById(userId);
    }

    // Retrieve pantry items for a user or household
    public List<PantryRecord> getPantryItems(String userId) {
        List<PantryRecord> items = pantryRepository.findByUserId(userId);

        for (PantryRecord item : items) {
            String description = foodCategories.get(item.getCategory());
            if (description != null) {
                item.setCategory(description);
            }

            // create logic to calculate isExpired
            item.setExpired(isItemExpired(item.getExpiryDate()));

        }
        return items;
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


    private boolean isItemExpired(String expiryDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date expiryDate = sdf.parse(expiryDateStr);
            return new Date().after(expiryDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }











    //for future dev
//    public void updatePantryFromRecipe (String userId, String recipeId) {
//
//        List<PantryRecord> pantry = pantryRepository.findByUserId(userId);
//        //same with recipe
//        RecipeRecord recipe = recipeRepository.findByRecipeId(recipeId);
//
//        //getIngredients returns list, how do I get individual ingredients?
//        //how do I get recipe ingredient quantities? ingredient.getQuantity?
//
//        // check if pantry has ingredient
////        if (pantry.contains(recipe.getIngredients())) {
//        // validation -  if pantryItem != null
//        if (pantry != null && recipe != null) {
//            for (PantryRecord record : pantry) {
//                for (Ingredient ingredient : recipe.getIngredients()) {
//                    if (record.getItemName().equalsIgnoreCase(ingredient.getIngredientName())) {
//                        //subtract amount used in recipe from pantry
//                        //update pantry with new amount
//                        record.setQuantity(record.getQuantity() - ingredient.getQuantity());
//                        //      pantry.setQuantity(pantry.getQuantity() - ingredient.getQuantity());
//                        //can't setQuanity in pantry because its a list
//                    }
//                }
//            }
//            // ** would need some sort of validation if pantry items < 0
//            // maybe if pantry item <= 0, set pantry item to 0
//            // (there have been times where I just bought stuff from the store just to
//            // make something, if i didnt have all ingredients)
//            //.save(pantry)
//        }
//
//    }

}
