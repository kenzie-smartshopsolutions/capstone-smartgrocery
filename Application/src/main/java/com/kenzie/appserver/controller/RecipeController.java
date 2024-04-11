package com.kenzie.appserver.controller;

import com.kenzie.appserver.repositories.model.RecipeRecord;
import com.kenzie.appserver.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Recipe")
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // Endpoint to retrieve all recipes
    @GetMapping
    public List<RecipeRecord> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    /*If the data is included in the request body (e.g., for creation or update operations),
    @RequestBody is used.
     If the data is part of the URL (e.g., for fetching or deleting resources),
     @PathVariable is used
     */

    // Endpoint to retrieve a specific recipe by its ID
    @GetMapping("/{recipeId}")
    public Optional<RecipeRecord> getRecipeById(@PathVariable String recipeId) {
        return recipeService.getRecipeById(recipeId);
    }
    // Endpoint to create a new recipe
    @PostMapping
    public RecipeRecord createRecipe(@RequestBody RecipeRecord recipeRecord) {
        return recipeService.createRecipe(recipeRecord);
    }
    // Endpoint to update an existing recipe
    @PutMapping("/{recipeId}")
    public void updateRecipe(@PathVariable String recipeId, @RequestBody RecipeRecord recipeRecord) {
        recipeService.updateRecipe(recipeId, recipeRecord);
    }
    // Endpoint to delete a recipe
    @DeleteMapping("/{recipeId}")
    public void deleteRecipe(@PathVariable String recipeId) {
        recipeService.deleteRecipe(recipeId);
    }
}

/*
    // Endpoint to retrieve all recipes
    @GetMapping
    public ResponseEntity<List<RecipeRecord>> getAllRecipes() {
        // Call the service to fetch all recipes
        List<RecipeRecord> recipes = recipeService.getAllRecipes();
        // Return the list of recipes along with the HTTP status code OK (200)
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    // Endpoint to retrieve a specific recipe by its ID
    @GetMapping("/{recipeId}")
    public ResponseEntity<RecipeRecord> getRecipeById(@PathVariable String recipeId) {
        // Call the service to fetch the recipe by its ID
        Optional<RecipeRecord> recipe = recipeService.getRecipeById(recipeId);
        // Check if the recipe is present
        return recipe.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        // If the recipe exists, return it with HTTP status code OK (200)
        // If the recipe does not exist, return HTTP status code NOT FOUND (404)
    }

    // Endpoint to create a new recipe
    @PostMapping
    public ResponseEntity<RecipeRecord> createRecipe(@RequestBody RecipeRecord recipeRecord) {
        // Call the service to create a new recipe
        RecipeRecord createdRecipe = recipeService.createRecipe(recipeRecord);
        // Return the created recipe with HTTP status code CREATED (201)
        return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
    }

    // Endpoint to update an existing recipe
    @PutMapping("/{recipeId}")
    public ResponseEntity<Void> updateRecipe(@PathVariable String recipeId, @RequestBody RecipeRecord recipeRecord) {
        // Call the service to update the recipe with the given ID
        recipeService.updateRecipe(recipeId, recipeRecord);
        // Return HTTP status code NO CONTENT (204) as the operation was successful
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint to delete a recipe
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String recipeId) {
        // Call the service to delete the recipe with the given ID
        recipeService.deleteRecipe(recipeId);
        // Return HTTP status code NO CONTENT (204) as the operation was successful
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    */
