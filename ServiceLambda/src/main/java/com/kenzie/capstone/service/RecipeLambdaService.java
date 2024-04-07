package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.RecipeDao;
import com.kenzie.capstone.service.model.RecipeData;

import javax.inject.Inject;

//needs modification
public class RecipeLambdaService {

    private RecipeDao recipeDao;

    @Inject
    public RecipeLambdaService(RecipeDao recipeDao) {
        this.recipeDao = recipeDao;
    }


    //needs modification
    public static RecipeData getRecipeData(String recipeId) {
        return null;
    }

    public static RecipeData setRecipeData(RecipeData recipeData) {
        return recipeData;
    }

    public void deleteRecipeData(String recipeId) {
        recipeDao.deleteRecipeRecord(recipeId);
    }

    public void updateRecipeData(RecipeData recipeData) {
        recipeDao.updateRecipeData(recipeData);
    }
}
