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

    //mapper DynamoDBMapper instance for interacting with DynamoDB
    public RecipeDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }


    public RecipeData storeRecipe(RecipeData recipeData) {
        try {
            // Save the recipe record with an expected condition that the recipe ID should not exist.
            mapper.save(recipeData, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "recipeId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            // If the recipe ID already exists, throw an IllegalArgumentException.
            throw new IllegalArgumentException("Recipe ID already exists: " + recipeData.getRecipeId());
        }
        return recipeData;
    }

    /**
     * Retrieves recipe data from the database based on recipe ID.
     *
     * @param recipeId The ID of the recipe to retrieve.
     * @return The retrieved recipe data.
     */
    public RecipeData getRecipeData(String recipeId) {
        // Load the RecipeRecord from the database using the provided recipe ID.
        RecipeRecord recipeRecord = mapper.load(RecipeRecord.class, recipeId);

        // Convert RecipeRecord to RecipeData
        RecipeData recipeData = convertToRecipeData(recipeRecord);

        // Return the retrieved recipe data.
        return recipeData;
    }

    private RecipeData convertToRecipeData(RecipeRecord recipeRecord) {
        if (recipeRecord == null) {
            return null;
        }

        RecipeData recipeData = new RecipeData();
        recipeData.setRecipeId(recipeRecord.getRecipeId());
        recipeData.setRecipeName(recipeRecord.getTitle());
        recipeData.setInstruction(recipeRecord.getInstructions());
        recipeData.setIngredients(recipeRecord.getIngredients());

        return recipeData;
    }

    /**
     * Sets recipe data in the database.
     *
     * @param recipeId   The ID of the recipe.
     * @param recipeData The recipe data to set.
     * @return The set recipe record.
     */
    public RecipeRecord setRecipeData(String recipeId, RecipeData recipeData) {
        // Create a new RecipeRecord object and set its properties using the provided recipe data.
        RecipeRecord recipeRecord = new RecipeRecord();
        recipeRecord.setRecipeId(recipeId);
        recipeRecord.setTitle(recipeData.getRecipeName());
        recipeRecord.setInstructions(recipeData.getInstruction());
        recipeRecord.setIngredients(recipeData.getIngredients());

        try {
            // Save the RecipeRecord to DynamoDB with an expected condition that ensures the recipeId does not already exist.
            mapper.save(recipeRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "recipeId",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            // If the recipeId already exists, throw an IllegalArgumentException.
            throw new IllegalArgumentException("Recipe ID already exists");
        }

        // Return the saved recipe record.
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
        // Load the existing recipe record from DynamoDB using the recipe ID from the provided recipe data.
        RecipeRecord recipeRecordToUpdate = mapper.load(RecipeRecord.class, recipeData.getRecipeId());

        // Check if the recipe record exists.
        if (recipeRecordToUpdate != null) {
            // If the recipe record exists, update its fields with the data from the provided recipe data.
            recipeRecordToUpdate.setTitle(recipeData.getRecipeName());
            recipeRecordToUpdate.setInstructions(recipeData.getInstruction());
            recipeRecordToUpdate.setIngredients(recipeData.getIngredients());

            // Save the updated recipe record to DynamoDB.
            mapper.save(recipeRecordToUpdate);
        } else {
            // If the recipe record doesn't exist, throw a RuntimeException indicating that the recipe was not found.
            throw new RuntimeException("Recipe not found with ID: " + recipeData.getRecipeId());
        }

        // Return the updated recipe data.
        return recipeData;
    }

    /**
     * Deletes a recipe record from the database.
     *
     * @param recipeId The ID of the recipe to delete.
     * @throws IllegalArgumentException If the recipe with the given ID does not exist.
     */
    public void deleteRecipeRecord(String recipeId) {
        // Attempt to load the recipe record from DynamoDB using the provided recipe ID.
        RecipeRecord recipeRecord = mapper.load(RecipeRecord.class, recipeId);

        // Check if the recipe record exists.
        if (recipeRecord != null) {
            // If the record exists, delete it from DynamoDB.
            mapper.delete(recipeRecord);
        } else {
            // If the record doesn't exist, throw an IllegalArgumentException.
            throw new IllegalArgumentException("Recipe not found with ID: " + recipeId);
        }
    }
}
