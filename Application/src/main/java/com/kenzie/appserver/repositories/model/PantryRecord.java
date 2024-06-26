package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import nonapi.io.github.classgraph.json.Id;

import java.util.Objects;

@DynamoDBTable(tableName = "Pantry")
public class PantryRecord {
    private String userId;
    private  String pantryItemId;
    private  String itemName;
    public String category;
    private  String expiryDate;
    private  int quantity;
//    private  boolean isExpired;
    private  String datePurchased;

    public PantryRecord(String userId, String pantryItemId, String itemName,
                        String category, int quantity, String expiryDate,
                        String datePurchased) {

        this.userId = userId;
        this.pantryItemId = pantryItemId;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
//        this.isExpired = isExpired;
        this.datePurchased = datePurchased;
    }
      
    public PantryRecord() {}


    @DynamoDBAttribute(attributeName = "userId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "userIdIndex", attributeName = "userId")
    public String getUserId() {
        return userId;
    }
      
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBHashKey(attributeName = "pantryItemId")
    public String getPantryItemId() {
        return pantryItemId;
    }
      
    public void setPantryItemId(String pantryItemId) {
        this.pantryItemId = pantryItemId;
    }
      
    @DynamoDBAttribute(attributeName = "itemName")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    @DynamoDBAttribute(attributeName = "expiryDate")
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
      
    @DynamoDBAttribute(attributeName = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
//    @DynamoDBAttribute(attributeName = "isExpired")
//    public boolean isExpired() {
//        return isExpired;
//    }
//
//    public void setIsExpired(boolean isExpired) {
//        this.isExpired = isExpired;
//    }
      
    @DynamoDBAttribute(attributeName = "datePurchased")
    public String getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(String datePurchased) {
        this.datePurchased = datePurchased;
    }

    @DynamoDBAttribute(attributeName = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PantryRecord that = (PantryRecord) o;
        return Objects.equals(pantryItemId, that.pantryItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pantryItemId);
    }
}
