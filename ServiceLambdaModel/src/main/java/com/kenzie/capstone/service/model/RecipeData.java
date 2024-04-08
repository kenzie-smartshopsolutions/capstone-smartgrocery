package com.kenzie.capstone.service.model;

import java.util.List;
import java.util.Objects;

public class RecipeData {
    private String recipeId;
    private String recipeName;
    private String instruction;
    private List<String> ingredients;

    /**
     * Default constructor.
     */
    public RecipeData() {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeData that = (RecipeData) o;
        return Objects.equals(recipeId, that.recipeId) && Objects.equals(recipeName, that.recipeName) && Objects.equals(instruction, that.instruction) && Objects.equals(ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, recipeName, instruction, ingredients);
    }
}
