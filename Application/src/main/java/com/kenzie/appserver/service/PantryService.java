package com.kenzie.appserver.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.kenzie.appserver.controller.model.pantry.PantryRequest;
import com.kenzie.appserver.repositories.PantryRepository;
import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.service.model.Pantry;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.PantryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class PantryService {

   // @Autowired
    private PantryRepository pantryRepository;
    private LambdaServiceClient lambdaServiceClient;

    private Map<String, String> foodCategories;


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
//    @PostConstruct // Execute after Bean initialization
//    public void loadFoodCategories() {
//
//        //???? hardcoding the filepath/
//       // String filePath = "C:\\Users\\12146\\kenzie\\ata-capstone-project-smartgrocery\\Application\\src\\main\\java\\com\\kenzie\\appserver\\repositories\\model\\food_category.csv";
//       // File file = new File(filePath);
//
//        String filePath = "/food_category.csv"; // Relative path to the CSV file
//
//        // Load the file from the classpath/resources
//        File file = new File(getClass().getResource(filePath).getFile());
//
//        try {
//            // Convert the CSV file to a map of food categories
//            foodCategories = FoodCategoryConverter.convertCsvToCategoryMap();
//        } catch (IOException e) {
//            // Handle error,  log a message
//            Logger.getLogger(PantryService.class.getName()).log(Level.SEVERE, "Error loading food categories", e);
//
//            // Provide a default value or handle the error accordingly
//            foodCategories = Collections.emptyMap(); // Default to an empty map
//        }
//    }


    // Retrieve pantry items for a user or household
    public List<PantryRecord> getPantryItems(String userId) {
        List<PantryData> lambdaPantryData = lambdaServiceClient.getPantryData(userId);

        List<PantryRecord> pantryRecord = lambdaPantryData.stream()
                .map(data -> new PantryRecord(
                        data.getUserId(),
                        data.getPantryItemId(),
                        data.getItemName(),
                        data.getCategory(),
                        data.getQuantity(),
                        data.getExpiryDate(),
//                        data.isExpired(),
                        data.getDatePurchased()
                ))
                .collect(Collectors.toList());

        return pantryRecord;
        }

public PantryRecord getByItemId(String pantryItemId) {
        PantryRecord pantryRecord = null;

        try {
            PantryData lambdaPantryData = lambdaServiceClient.getPantryItemData(pantryItemId);

            Pantry lambdaPantry = new Pantry(
                    lambdaPantryData.getUserId(),
                    lambdaPantryData.getPantryItemId(),
                    lambdaPantryData.getItemName(),
                    lambdaPantryData.getCategory(),
                    lambdaPantryData.getQuantity(),
                    lambdaPantryData.getExpiryDate(),
                    lambdaPantryData.getDatePurchased()

            );
            pantryRecord = convertFromDto(lambdaPantry);
        }catch (Exception e) {
            System.err.println("Unable to retrieve data from the Lambda function: " + e.getMessage());

            pantryRecord = pantryRepository.findItemByPantryItemId(pantryItemId);

//            if (pantryRecord == null) {
//                throw new IllegalArgumentException("Item not found in databases.");
//            }
        }
        return pantryRecord;
}

    /**
//     * Adds a new pantry item to the repository.
//     *
//     * @param pantryRecord The pantry item to be added.
//     * @return The added pantry item.
     */
    public PantryRecord addPantryItem(PantryRequest request) {
        //??Perform validation before adding the pantry item
        PantryRecord pantryRecord = new PantryRecord();
        pantryRecord.setDatePurchased(request.getDatePurchased());
//        pantryRecord.setIsExpired(request.isExpired());
        pantryRecord.setPantryItemId(generatePantryItemId());
        pantryRecord.setCategory(request.getCategory());
        pantryRecord.setUserId(request.getUserId());
        pantryRecord.setExpiryDate(request.getExpiryDate());
        pantryRecord.setQuantity(request.getQuantity());
        pantryRecord.setItemName(request.getItemName());

        PantryData pantryData = convertToData(pantryRecord);
        lambdaServiceClient.setPantryData(pantryData);

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

public PantryRecord updatePantryItem(PantryRecord pantryRecord) {
    return pantryRepository.save(pantryRecord);
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
       // PantryRecord record = pantryRepository.findItemByPantryItemId(pantryItemId);
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
    public PantryRecord convertFromDto(Pantry pantry) {
        PantryRecord pantryRecord = new PantryRecord();
        pantryRecord.setUserId(pantry.getUserId());
        pantryRecord.setPantryItemId(pantryRecord.getPantryItemId());
        pantryRecord.setItemName(pantry.getItemName());
        pantryRecord.setCategory(pantryRecord.getCategory());
        pantryRecord.setExpiryDate(pantry.getExpiryDate());
        pantryRecord.setQuantity(pantryRecord.getQuantity());
//        pantryRecord.setIsExpired(pantry.isExpired());
        pantryRecord.setDatePurchased(pantry.getDatePurchased());
        return pantryRecord;
    }
    public Pantry convertRecordToDto(PantryRecord pantryRecord) {
        Pantry pantry = new Pantry(
                pantryRecord.getUserId(),
                pantryRecord.getPantryItemId(),
                pantryRecord.getItemName(),
                pantryRecord.getCategory(),
                pantryRecord.getQuantity(),
                pantryRecord.getExpiryDate(),
//                pantryRecord.isExpired(),
                pantryRecord.getDatePurchased()
        );

        return pantry;
    }
//    private PantryResponse createPantryResponse(PantryRecord pantry) {
//        PantryResponse pantryResponse = new PantryResponse();
//        pantryResponse.setPantryItemId(pantry.getPantryItemId());
//        pantryResponse.setUserId(pantry.getUserId());
//        pantryResponse.setDatePurchased(pantry.getDatePurchased());
//        pantryResponse.setExpired(pantry.isExpired());
//        pantryResponse.setExpiryDate(pantry.getExpiryDate());
//        pantryResponse.setItemName(pantry.getItemName());
//        return pantryResponse;
//    }

    private PantryData convertToData(PantryRecord pantryRecord) {
        PantryData pantryData = new PantryData(
                pantryRecord.getUserId(),
                pantryRecord.getPantryItemId(),
                pantryRecord.getItemName(),
                pantryRecord.getCategory(),
                pantryRecord.getQuantity(),
                pantryRecord.getExpiryDate(),
//                pantryRecord.isExpired(),
                pantryRecord.getDatePurchased()
        );

        return pantryData;
    }
    private String generatePantryItemId() {
        return UUID.randomUUID().toString();
    }
}
