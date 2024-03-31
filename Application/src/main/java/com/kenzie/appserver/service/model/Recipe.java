package com.kenzie.appserver.service.model;

import com.kenzie.appserver.repositories.model.PantryRecord;

import java.util.List;

public class Recipe {
    /*recipeId (String)
title (String)
ingredients (List<Ingredient>)
instructions (String)
*/
    private final String recipeId;
    private final String title;
    private final List<PantryRecord> ingredients;
    private final String instructions;


    public Recipe(String recipeId, String title, List<PantryRecord> ingredients, String instructions) {
        this.recipeId = recipeId;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }


    public String getRecipeId() {
        return recipeId;
    }

    public String getTitle() {
        return title;
    }

    public List<PantryRecord> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
}
