package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.RecipeData;
import com.kenzie.capstone.service.model.RecipeRecord;

import java.util.UUID;

public class RecipeDao {

    private DynamoDBMapper mapper;

    public RecipeDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }
    /**
     * Stores recipe data in the database.
     *
     * @param recipeData The recipe data to store.
     * @return The stored recipe data.
     */
    public RecipeData storeRecipeData(RecipeData recipeData) {
        try {
            mapper.save(recipeData, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "recipeId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("Recipe ID has already been used");
        }

        return recipeData;
    }

    /**
     * Retrieves recipe data from the database based on recipe ID.
     *
     * @param recipeId The ID of the recipe to retrieve.
     * @return The retrieved recipe data.
     */
    public RecipeRecord getRecipeData(String recipeId) {
        RecipeRecord recipeRecord = mapper.load(RecipeRecord.class, recipeId);
        return recipeRecord;
    }

    /**
     * Sets recipe data in the database.
     *
     * @param recipeId   The ID of the recipe.
     * @param recipeData The recipe data to set.
     * @return The set recipe record.
     */
    public RecipeRecord setRecipeData(String recipeId, RecipeData recipeData) {
        RecipeRecord recipeRecord = new RecipeRecord();
        recipeRecord.setRecipeId(recipeId);
        recipeRecord.setTitle(recipeData.getRecipeName());
        recipeRecord.setInstructions(recipeData.getInstruction());
        recipeRecord.setIngredients(recipeData.getIngredients());

        try {
            mapper.save(recipeRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "recipeId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("Recipe ID already exists");
        }

        return recipeRecord;
    }

    /**
     * Updates existing recipe data in the database.
     *
     * @param recipeData The updated recipe data.
     * @return The updated recipe data.
     * @throws RuntimeException If the recipe with the given ID does not exist.
     */
    public RecipeData updateRecipeData(RecipeData recipeData) {

        RecipeRecord recipeRecordToUpdate = mapper.load(RecipeRecord.class, recipeData.getRecipeId());

        if (recipeRecordToUpdate != null) {
            // Update the RecipeRecord with the data from RecipeData
            recipeRecordToUpdate.setTitle(recipeData.getRecipeName());
            recipeRecordToUpdate.setInstructions(recipeData.getInstruction());
            recipeRecordToUpdate.setIngredients(recipeData.getIngredients());
            mapper.save(recipeRecordToUpdate);
        } else {
            throw new RuntimeException("Recipe not found with ID: " + recipeData.getRecipeId());
        }
        return recipeData;
    }

    /**
     * Deletes a recipe record from the database.
     *
     * @param recipeId The ID of the recipe to delete.
     * @throws IllegalArgumentException If the recipe with the given ID does not exist.
     */
    public void deleteRecipeRecord(String recipeId) {
        RecipeRecord recipeRecord = mapper.load(RecipeRecord.class, recipeId);
        if (recipeRecord != null) {
            mapper.delete(recipeRecord);
        } else {
            throw new IllegalArgumentException("Recipe not found with ID: " + recipeId);
        }
    }
}
