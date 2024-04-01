package com.kenzie.appserver.service.model;

import com.kenzie.appserver.repositories.model.PantryRecord;

import java.util.List;

public class Recipe {
    /*recipeId (String)
title (String)
ingredients (List<Ingredient>)


blocker: List<String> ingredients; or List<PantryRecord> ingredients; ?????
***no longer using ingredient table instead-> ingredients as a list of string****
instructions (String)
*/
    private final String recipeId;
    private final String title;
    private final List<String> ingredients;
    private final String instructions;

    public Recipe(String recipeId, String title, List<String> ingredients, String instructions) {
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

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
}
