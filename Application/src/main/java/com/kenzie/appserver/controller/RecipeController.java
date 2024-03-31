package com.kenzie.appserver.controller;

import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class RecipeController {
    @Autowired
    RecipeService recipeService;


    @GetMapping("/{ingredient}")
    public ResponseEntity<RecipeRecord> getRecipeByIngredient(@PathVariable String ingredient) {
        RecipeRecord recipeRecord = recipeService.getRecipeByIngredient(ingredient);
        return new ResponseEntity<>(recipeRecord, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<RecipeRecord> addRecipe(@RequestBody RecipeRecord recipeRecord) {
        RecipeRecord recipe = recipeService.addNewRecipe(recipeRecord);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<RecipeRecord> updateRecipe(
            @PathVariable String recipeID, @RequestBody RecipeRecord recipeRecord) {
        if (recipeService.getRecipe(recipeID) != null) {
            recipeRecord.setRecipeId(recipeID);
            RecipeRecord updatedRecipe = recipeService.updateRecipe(recipeRecord);
            return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String recipeId) {
        if (recipeService.getRecipe(recipeId) != null) {
            recipeService.deleteRecipe(recipeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

