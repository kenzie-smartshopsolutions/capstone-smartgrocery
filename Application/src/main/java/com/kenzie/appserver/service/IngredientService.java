package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.IngredientRepository;
import com.kenzie.appserver.repositories.PantryRepository;
import com.kenzie.appserver.repositories.model.IngredientRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import io.swagger.models.auth.In;

public class IngredientService {
    private IngredientRepository ingredientRepository;

    private LambdaServiceClient lambdaServiceClient;

    public IngredientService(IngredientRepository ingredientRepository, LambdaServiceClient lambdaServiceClient) {
        this.ingredientRepository = ingredientRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }

/*Ingredients Model (facade?):  Stores ingredient details
addIngredient()
updateIngredient()
updateQuantity()
updatePrice()
*/
    public IngredientRecord addIngredient(IngredientRecord ingredientRecord) {
        return ingredientRepository.save(ingredientRecord);
    }
    public IngredientRecord updateIngredient(IngredientRecord ingredientRecord) {
        return ingredientRepository.save(ingredientRecord);
    }
    public IngredientRecord getIngredient(String ingredientId) {
        return ingredientRepository.findById(ingredientId).orElse(null);
    }
    //delete?
}
