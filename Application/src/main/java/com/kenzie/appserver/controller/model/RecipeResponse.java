//package com.kenzie.appserver.controller.model;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.kenzie.appserver.repositories.model.PantryRecord;
//
//import java.util.List;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class RecipeResponse {
//    @JsonProperty("recipeId")
//    private  String recipeId;
//    @JsonProperty("title")
//    private  String title;
//    @JsonProperty("ingredients")
//    private List<PantryRecord> ingredients;
//    @JsonProperty("instructions")
//    private  String instructions;
//
//    public String getRecipeId() {
//        return recipeId;
//    }
//
//    public void setRecipeId(String recipeId) {
//        this.recipeId = recipeId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public List<PantryRecord> getIngredients() {
//        return ingredients;
//    }
//
//    public void setIngredients(List<PantryRecord> ingredients) {
//        this.ingredients = ingredients;
//    }
//
//    public String getInstructions() {
//        return instructions;
//    }
//
//    public void setInstructions(String instructions) {
//        this.instructions = instructions;
//    }
//}