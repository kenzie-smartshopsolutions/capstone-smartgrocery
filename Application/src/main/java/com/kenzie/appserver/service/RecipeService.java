package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.RecipeRepository;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//import org.springframework.stereotype.Service;
//
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<RecipeRecord> getAllRecipes() {
        return (List<RecipeRecord>) recipeRepository.findAll();
    }

    public Optional<RecipeRecord> getRecipeById(String id) {
        return recipeRepository.findById(id);
    }

    public RecipeRecord createRecipe(RecipeRecord recipeRecord) {
        return recipeRepository.save(recipeRecord);
    }

    public void updateRecipe(String id, RecipeRecord recipeRecord) {
        recipeRecord.setRecipeId(id);
        recipeRepository.save(recipeRecord);
    }

    public void deleteRecipe(String id) {
        recipeRepository.deleteById(id);
    }
}
