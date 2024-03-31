package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.ExampleResponse;
import com.kenzie.appserver.controller.model.PantryResponse;
import com.kenzie.appserver.controller.model.PantryUpdateRequest;
import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.service.PantryService;
//import com.kenzie.appserver.service.model.Pantry;
import com.kenzie.appserver.service.model.Example;
import com.kenzie.appserver.service.model.Pantry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("/pantry")
public class PantryController {

    @Autowired
    private PantryService pantryService;

    // Get pantry items for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<PantryRecord>> getPantryItems(@PathVariable String userId) {
//        // Need to verify the logged-in user matches the userId:
//        if (!authentication.getName().equals(userId)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
        List<PantryRecord> pantryItems = pantryService.getPantryItems(userId);
        return new ResponseEntity<>(pantryItems, HttpStatus.OK);

    }

    // Add a new pantry item
    @PostMapping
    public ResponseEntity<PantryRecord> addPantryItem(@RequestBody PantryRecord pantryRecord) {
        PantryRecord addedItem = pantryService.addPantryItem(pantryRecord);
        addedItem.setItemName(addedItem.getItemName());
        addedItem.setQuantity(addedItem.getQuantity());
        addedItem.setDatePurchased(addedItem.getDatePurchased());
        addedItem.setExpiryDate(addedItem.getExpiryDate());
        addedItem.setCategory(addedItem.getCategory());
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    // Update an existing pantry item
    //???
    @PutMapping("/{pantryItemId}")
    public ResponseEntity<PantryRecord> updatePantryItem(
            @PathVariable String pantryItemId, @RequestBody PantryRecord pantryRecord) {
        if (pantryService.getPantryItems(pantryItemId) != null) {
            pantryRecord.setPantryItemId(pantryItemId);
            PantryRecord updatedItem = pantryService.updatePantryItem(pantryRecord);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a pantry item by ID
    //????
    @DeleteMapping("/{pantryItemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable("petId") String pantryItemId) {
        pantryService.deletePantryItem(pantryItemId);
        return ResponseEntity.status(204).build();
     }
//    public ResponseEntity<Void> deletePantryItem(@PathVariable String pantryItemId) {
//        if (pantryService.getPantryItems(pantryItemId) != null) {
//            pantryService.deletePantryItem(pantryItemId);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//private PantryResponse createPantryResponse(Pantry pantry) {
//    PantryResponse pantryResponse = new PantryResponse();
//    pantryResponse.setCatagoryId(pantry.getCatagoryId());
//    pantryResponse.setDatePurchased(pantry.getDatePurchased());
//    pantryResponse.setExpired(pantry.isExpired());
//    pantryResponse.setExpiryDate(pantry.getExpiryDate());
//    pantryResponse.setItemName(pantry.getItemName());
//    return pantryResponse;
//}
}
