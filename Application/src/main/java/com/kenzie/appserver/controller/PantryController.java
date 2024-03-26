package com.kenzie.appserver.controller;

import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.service.PantryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("Pantry")
public class PantryController {


    private PantryService pantryService;

    @Autowired
    public PantryController(PantryService pantryService) {
        this.pantryService = pantryService;
    }

    // Get pantry items for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<PantryRecord>> getPantryItems(@PathVariable String userId) {
//        // Need to verify the logged-in user matches the userId:
//        if (!authentication.getName().equals(userId)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
        List<PantryRecord> pantryItems = pantryService.getPantryItems(userId);
        // return new ResponseEntity<>(pantryItems, HttpStatus.OK);
        return ResponseEntity.ok(pantryItems);
    }

    // Add a new pantry item
    @PostMapping
    public ResponseEntity<PantryRecord> addPantryItem(@RequestBody PantryRecord pantryRecord) {
        PantryRecord addedItem = pantryService.addPantryItem(pantryRecord);
        // return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedItem);
    }

    // Update an existing pantry item
    //???
    @PutMapping("/{pantryItemId}")
    public ResponseEntity<PantryRecord> updatePantryItem(
            @PathVariable String pantryItemId, @RequestBody PantryRecord pantryRecord) {
        // Call the service layer to update the pantry item
        PantryRecord updatedItem = pantryService.updatePantryItem(pantryItemId, pantryRecord);

        // Check if the item was successfully updated
        if (updatedItem != null) {
            // Return a success response with the updated pantry item
            return ResponseEntity.ok(updatedItem);
        } else {
            // Return a not found response if the pantry item with the given ID was not found
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a pantry item by ID
    //????
    @DeleteMapping("/{pantryItemId}")
    public ResponseEntity<Void> deletePantryItem(@PathVariable String pantryItemId) {
        pantryService.deletePantryItem(pantryItemId);
        return ResponseEntity.noContent().build();
    }
}