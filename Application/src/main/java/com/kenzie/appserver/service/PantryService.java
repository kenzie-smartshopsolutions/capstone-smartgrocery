package com.kenzie.appserver.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kenzie.appserver.repositories.PantryRepository;

//import com.kenzie.appserver.repositories.RecipeRepository;

import com.kenzie.appserver.repositories.model.FoodCategoryConverter;

import com.kenzie.appserver.repositories.model.PantryRecord;
//import com.kenzie.appserver.repositories.model.RecipeRecord;
//import com.kenzie.appserver.service.model.Ingredient;
//import com.kenzie.appserver.service.model.Pantry;
import com.kenzie.capstone.service.client.LambdaServiceClient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import java.util.Optional;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class PantryService {

   // @Autowired
    private PantryRepository pantryRepository;
    private LambdaServiceClient lambdaServiceClient;

    private Map<String, String> foodCategories;

    private LambdaServiceClient lambdaServiceClient;

    @Autowired
    public PantryService(PantryRepository pantryRepository, LambdaServiceClient lambdaServiceClient) {
        this.pantryRepository = pantryRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    //???

    /**
     * Loads the food categories from a CSV file into a map during bean initialization.
     * The CSV file should contain mappings of food categories to their descriptions.
     */
    @PostConstruct // Execute after Bean initialization
    public void loadFoodCategories() {

        //???? hardcoding the filepath/
       // String filePath = "C:\\Users\\12146\\kenzie\\ata-capstone-project-smartgrocery\\Application\\src\\main\\java\\com\\kenzie\\appserver\\repositories\\model\\food_category.csv";
       // File file = new File(filePath);

        String filePath = "/food_category.csv"; // Relative path to the CSV file

        // Load the file from the classpath/resources
        File file = new File(getClass().getResource(filePath).getFile());

        try {
            // Convert the CSV file to a map of food categories
            foodCategories = FoodCategoryConverter.convertCsvToCategoryMap();
        } catch (IOException e) {
            // Handle error,  log a message
            Logger.getLogger(PantryService.class.getName()).log(Level.SEVERE, "Error loading food categories", e);

            // Provide a default value or handle the error accordingly
            foodCategories = Collections.emptyMap(); // Default to an empty map
        }
    }


    // Retrieve pantry items for a user
    /**
     * Retrieves the pantry items for a specific user.
     * Each pantry item's category is mapped to its corresponding description.
     * Checks if each item has expired based on its expiry date.
     *
     * @param userId The ID of the user whose pantry items are being retrieved.
     * @return A list of pantry items belonging to the specified user.
     */

//    @PostConstruct // Execute after Bean initialization
//    public void loadFoodCategories() {
//        File file = new File("/food_category.csv"); // Replace with the correct path
//        try {
//            foodCategories = FoodCategoryConverter.convertCsvToCategoryMap();
//        } catch (IOException e) {
//            // Handle error, maybe log a message
//            e.printStackTrace();
//        }
//    }


    public Optional<PantryRecord> getPantry(String userId) {
        return pantryRepository.findById(userId);
    }

 //    Retrieve pantry items for a user or household
    public List<PantryRecord> getPantryItems(String userId) {
        // return pantryRepository.findByUserId(userId);

        // Fetch pantry items from the repository for the given user ID
        List<PantryRecord> items = pantryRepository.findByUserId(userId);

        //Map category  to descriptions
        for (PantryRecord item : items) {
            String description = foodCategories.get(item.getCategory());
            if (description != null) {
                item.setCategory(description);
            }

            // if the item is expired based on expiry date
            item.setExpired(isItemExpired(item.getExpiryDate()));

        }
        return items;
   }

/*

    // Add a new pantry item
    public PantryRecord addPantryItem(String userId, String pantryItemId, String itemName, String category,
                                      String expiryDate, int quantity, boolean isExpired, Date datePurchased) {
        // Create a new pantry record object
        PantryRecord pantryRecord = new PantryRecord();

        // Set the properties of the pantry item
        pantryRecord.setUserId(userId);
        pantryRecord.setPantryItemId(pantryItemId);
        pantryRecord.setItemName(itemName);
        pantryRecord.setCategory(category);
        pantryRecord.setExpiryDate(expiryDate);
        pantryRecord.setQuantity(quantity);
        pantryRecord.setExpired(isExpired);
        pantryRecord.setDatePurchased(datePurchased);

        // Save the pantry item to the repository
        return pantryRepository.save(pantryRecord);
    }

 */
    /**
     * Adds a new pantry item to the repository.
     *
     * @param pantryRecord The pantry item to be added.
     * @return The added pantry item.
     */
    public PantryRecord addPantryItem(PantryRecord pantryRecord) {
        //??Perform validation before adding the pantry item
        return pantryRepository.save(pantryRecord);
    }
      /*
        // for interacting with Lambda service
        PantryData pantryDataFromLambda = lambdaServiceClient.setPantryData(pantryRecord);

        // for saving data to the local repository
        pantryRepository.save(pantryDataFromLambda);

        // Construct and return the response object
        return pantryDataFromLambda;*/


    // Update an existing pantry item

    public PantryRecord updatePantryItem(String pantryItemId, PantryRecord updatedPantryRecord) {
        // Check if the pantry item exists before updating
        if (pantryRepository.existsById(pantryItemId)) {
            updatedPantryRecord.setPantryItemId(pantryItemId);
            return pantryRepository.save(updatedPantryRecord);
        }
        return null; // Return null if item not found
    }

    // Delete a pantry item by ID
    /**
     * Deletes a pantry item from the repository by its ID.
     *
     * @param pantryItemId The ID of the pantry item to be deleted.
     */

   //  Delete a pantry item by ID
    public void deletePantryItem(String pantryItemId) {
        // Check if the pantry item exists before deleting
        if (pantryRepository.existsById(pantryItemId)) {
            pantryRepository.deleteById(pantryItemId);
        } else {
            throw new NotFoundException("Pantry item not found with ID: " + pantryItemId);
        }
    }


    // parses the expiry date string into a Date
    //checks if it is in the past, indicating that the item has expired
    //logs an error if parsing the expiry date string fails due to a ParseException
    //If the parsing fails/couldn't determine the expiry date due to an incorrect format or any other parsing issue



    /**
     * Parses the expiry date string into a Date object and checks if it is in the past, indicating that the item has expired.
     * Logs an error if parsing the expiry date string fails due to a ParseException.
     * If the parsing fails, returns false as a default value.
     *
     * @param expiryDateStr The expiry date string to be parsed.
     * @return true if the item has expired, otherwise false.
     */

    private boolean isItemExpired(String expiryDateStr) {
        // Implement expiry date checking logic
        // Date format for parsing expiry date string
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


        try {
            Date expiryDate = sdf.parse(expiryDateStr);
            return new Date().after(expiryDate);

        } catch (ParseException e) {
            Logger.getLogger(PantryService.class.getName()).log(Level.SEVERE, "Error parsing expiry date", e);
           // e.printStackTrace();
            return false;
        }
    }
}
