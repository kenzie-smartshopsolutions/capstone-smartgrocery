package com.kenzie.appserver.service.model;

import java.util.List;

public class Recipe {
    /*recipeId (String)
title (String)
ingredients (List<Ingredient>)
instructions (String)
*/
    private final String recipeId;
    private final String title;
   // private final List<Ingredient> ingredients;
    private final String instructions;


    public Recipe(String recipeId, String title, String instructions) {
        this.recipeId = recipeId;
        this.title = title;
       // this.ingredients = ingredients;
        this.instructions = instructions;
    }


    public String getRecipeId() {
        return recipeId;
    }

    public String getTitle() {
        return title;
    }

   // public List<Ingredient> getIngredients() {  return ingredients;}



    public String getInstructions() {
        return instructions;
    }
}
