package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.Objects;

public class PantryData {
    private String userId;
    private String pantryItemId;
    private String itemName;
    private String expiryDate;
    private int quantity;
    @JsonIgnore
    private boolean isExpired;
    private String datePurchased;
    private String category;
    @JsonIgnoreProperties(ignoreUnknown = true)


    public PantryData(String userId, String pantryItemId, String itemName,
    String category, int quantity, String expiryDate,
    boolean isExpired, String datePurchased){

        this.pantryItemId = pantryItemId;
        this.itemName = itemName;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.isExpired = isExpired;
        this.datePurchased = datePurchased;
        this.category = category;
        this.userId = userId;
    }
    public PantryData() {

    }

    public String getPantryItemId() {
        return pantryItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public String getDatePurchased() {
        return datePurchased;
    }

     public String getCategory() {
       return category;
     }


    public String getUserId() {
        return userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PantryData that = (PantryData) o;
        return quantity == that.quantity && isExpired == that.isExpired && Objects.equals(pantryItemId, that.pantryItemId) && Objects.equals(itemName, that.itemName) && Objects.equals(expiryDate, that.expiryDate) && Objects.equals(datePurchased, that.datePurchased) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pantryItemId, itemName, expiryDate, quantity, isExpired, datePurchased, category);
    }
}
