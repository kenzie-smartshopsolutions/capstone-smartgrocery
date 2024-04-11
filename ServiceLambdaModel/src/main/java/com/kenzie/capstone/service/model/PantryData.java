package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import java.util.Date;
import java.util.Objects;

public class PantryData {
    public String userId;
    private final String pantryItemId;
    private final String itemName;
    private final String expiryDate;
    private final int quantity;
    private final boolean isExpired;
    private final Date datePurchased;
    public final String category;





    public PantryData(String pantryItemId, String itemName, String expiryDate, int quantity, boolean isExpired, Date datePurchased, String category, String userId) {
        this.pantryItemId = pantryItemId;
        this.itemName = itemName;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.isExpired = isExpired;
        this.datePurchased = datePurchased;
        this.category = category;
        this.userId = userId;
        // this.dateUsed = dateUsed;
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

    public Date getDatePurchased() {
        return datePurchased;
    }

     public String getCategory() {
       return category;
     }

    // public Date getDateUsed() {
    //   return dateUsed;
//}
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
