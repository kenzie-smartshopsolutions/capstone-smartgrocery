package com.kenzie.appserver.controller;

import com.kenzie.appserver.repositories.model.IngredientRecord;
import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class IngredientController {
    IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<IngredientRecord> addIngredient(@RequestBody IngredientRecord ingredientRecord) {
        IngredientRecord addedItem = ingredientService.addIngredient(ingredientRecord);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @PutMapping("/{IngredientId}")
    public ResponseEntity<IngredientRecord> updateIngredient(
            @PathVariable String ingredientId, @RequestBody IngredientRecord ingredientRecord) {
        if (ingredientService.getIngredient(ingredientId) != null) {
            ingredientRecord.setIngredientId(ingredientId);
            IngredientRecord updatedIngredient = ingredientService.updateIngredient(ingredientRecord);
            return new ResponseEntity<>(updatedIngredient, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
