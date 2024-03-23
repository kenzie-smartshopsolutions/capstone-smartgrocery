package com.kenzie.appserver.service;


import com.kenzie.appserver.controller.RecipeController;
import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;


//import org.springframework.stereotype.Service;
//
//@Service

public class RecipeService {

    private LambdaServiceClient lambdaServiceClient;

    private RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository, LambdaServiceClient lambdaServiceClient){
        this.recipeRepository = recipeRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    public RecipeRecord getRecipe(String recipeId){
       return recipeRepository.findByRecipeId(recipeId);
    }
    public RecipeRecord getRecipeByIngredient(String ingredient){
       return recipeRepository.findByIngredient(ingredient);
    }
    public RecipeRecord addNewRecipe(RecipeRecord recipeRecord){
        return recipeRepository.save(recipeRecord);
    }
    public RecipeRecord updateRecipe(RecipeRecord recipeRecord) {
        return recipeRepository.save(recipeRecord);
    }
    public void deleteRecipe(String recipeId){
        recipeRepository.deleteById(recipeId);
    }

}
