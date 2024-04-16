package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;
@DynamoDBTable(tableName = "Recipe")
public class RecipeRecord {
    private  String recipeId;
    private  String title;
    private List<String> ingredients; // Adjusted data type
    private  String instructions;

    public RecipeRecord() {
        // Default constructor required by DynamoDB
    }

    public RecipeRecord(String recipeId, String title, List<String> ingredients, String instructions) {
        this.recipeId = recipeId;
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    @DynamoDBHashKey(attributeName = "RecipeId")
    public String getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }
    @DynamoDBAttribute(attributeName = "Title")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @DynamoDBAttribute(attributeName = "Ingredients")
    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @DynamoDBAttribute(attributeName = "Instructions")
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeRecord that = (RecipeRecord) o;
        return Objects.equals(recipeId, that.recipeId) && Objects.equals(title, that.title) && Objects.equals(ingredients, that.ingredients) && Objects.equals(instructions, that.instructions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, title, ingredients, instructions);
    }
}
