package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.PantryRepository;
import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PantryService {

    private PantryRepository pantryRepository;

    private LambdaServiceClient lambdaServiceClient;

    public PantryService(PantryRepository pantryRepository, LambdaServiceClient lambdaServiceClient) {
        this.pantryRepository = pantryRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    // Retrieve pantry items for a user or household
    public List<PantryRecord> getPantryItems(String userId) {
        return pantryRepository.findByUserId(userId);
    }

    // Add a new pantry item
    public PantryRecord addPantryItem(PantryRecord pantryRecord) {
        return pantryRepository.save(pantryRecord);
    }

    // Update an existing pantry item
    public PantryRecord updatePantryItem(PantryRecord pantryRecord) {
        return pantryRepository.save(pantryRecord);
    }

    // Delete a pantry item by ID
    public void deletePantryItem(String pantryItemId) {
        pantryRepository.deleteById(pantryItemId);
    }
}
