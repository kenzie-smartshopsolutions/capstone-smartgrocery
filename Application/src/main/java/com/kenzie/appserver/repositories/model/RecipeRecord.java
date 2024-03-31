//package com.kenzie.appserver.repositories.model;
//
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
////import com.kenzie.appserver.service.model.Ingredient;
//
//import java.util.List;
//import java.util.Objects;
//@DynamoDBTable(tableName = "Recipe")
//public class RecipeRecord {
//    private  String recipeId;
//    private  String title;
//    //private  List<Ingredient> ingredients;
//    private  String instructions;
//    @DynamoDBHashKey(attributeName = "RecipeId")
//    public String getRecipeId() {
//        return recipeId;
//    }
//
//    public void setRecipeId(String recipeId) {
//        this.recipeId = recipeId;
//    }
//    @DynamoDBAttribute(attributeName = "Title")
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
////    @DynamoDBAttribute(attributeName = "Ingredients")
////    public List<Ingredient> getIngredients() {
////        return ingredients;
////    }
////
////    public void setIngredients(List<Ingredient> ingredients) {
////        this.ingredients = ingredients;
////    }
//    @DynamoDBAttribute(attributeName = "Instructions")
//    public String getInstructions() {
//        return instructions;
//    }
//
//    public void setInstructions(String instructions) {
//        this.instructions = instructions;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        RecipeRecord that = (RecipeRecord) o;
//        return Objects.equals(recipeId, that.recipeId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(recipeId);
//    }
//}
