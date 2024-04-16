package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.PantryRecord;
import com.kenzie.capstone.service.model.RecipeData;
import com.kenzie.capstone.service.model.RecipeRecord;
import com.kenzie.capstone.service.model.UserRecord;

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
    public RecipeRecord getRecipeData(String recipeId) {
        RecipeRecord recipeRecord= new RecipeRecord();

        if (recipeId == null || recipeId.isEmpty() || recipeId.isBlank()) {
            throw new IllegalArgumentException("RecipeId cannot be null or empty");
        }else {
            recipeRecord.setRecipeId(recipeId);
        }
        DynamoDBQueryExpression<RecipeRecord> queryExpression = new DynamoDBQueryExpression<RecipeRecord>()
                .withHashKeyValues(recipeRecord)
                .withConsistentRead(false);
        return mapper.query(RecipeRecord.class, queryExpression).get(0);
    }

    public RecipeData convertToRecipeData(RecipeRecord recipeRecord) {
        if (recipeRecord == null) {
            return null;
        }
        return new RecipeData(recipeRecord.getRecipeId(),
                recipeRecord.getTitle(),
                recipeRecord.getInstructions(),
                recipeRecord.getIngredients());
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

        if (recipeId == null || recipeId.isEmpty() || recipeId.isBlank()) {
            String uniqueRecipeId = UUID.randomUUID().toString();
            recipeRecord.setRecipeId(uniqueRecipeId);
        }
        RecipeRecord tempData = convertToRecipeRecord(recipeData);

        mapper.save(tempData);
        return recipeRecord;
    }

    public RecipeRecord convertToRecipeRecord(RecipeData recipeData) {
        return new RecipeRecord(recipeData.getRecipeId(),
                recipeData.getTitle(),
                recipeData.getIngredients(),
               recipeData.getInstructions());
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
            recipeRecordToUpdate.setTitle(recipeData.getTitle());
            recipeRecordToUpdate.setInstructions(recipeData.getInstructions());
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
