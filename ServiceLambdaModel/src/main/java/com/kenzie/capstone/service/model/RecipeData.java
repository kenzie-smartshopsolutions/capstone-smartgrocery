package com.kenzie.capstone.service.model;

import java.util.List;
import java.util.Objects;

public class RecipeData {
    private String recipeId;
    private String title;
    private String instructions;
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
     * @param title  The name of the recipe.
     * @param instructions  The cooking instructions for the recipe.
     * @param ingredients  The list of ingredients required for the recipe.
     */
    public RecipeData(String recipeId, String title, String instructions, List<String> ingredients) {
        this.recipeId = recipeId;
        this.title = title;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }


    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
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
        return Objects.equals(recipeId, that.recipeId) && Objects.equals(title, that.title) && Objects.equals(instructions, that.instructions) && Objects.equals(ingredients, that.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, title, instructions, ingredients);
    }
}
