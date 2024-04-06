package com.kenzie.capstone.service.model;

import java.util.List;

public class RecipeData {
    private String recipeId;
    private String recipeName;
    private String instruction;
    private List<String> ingredients;

    /**
     * Default constructor.
     */
    public RecipeData() {
        // Default constructor required by DynamoDBMapper
    }

    /**
     * Parameterized constructor.
     *
     * @param recipeId     The unique identifier for the recipe.
     * @param recipeName   The name of the recipe.
     * @param instruction  The cooking instructions for the recipe.
     * @param ingredients  The list of ingredients required for the recipe.
     */
    public RecipeData(String recipeId, String recipeName, String instruction, List<String> ingredients) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.instruction = instruction;
        this.ingredients = ingredients;
    }


    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
