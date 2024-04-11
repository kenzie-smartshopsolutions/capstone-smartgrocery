package com.kenzie.appserver.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;


public class Pantry {
    private final String userId;
    private final String pantryItemId;
    private final String itemName;
    public final String category;
    private final String expiryDate;
    private final int quantity;
//    private final boolean isExpired;
    private final String datePurchased;



    public Pantry(String userId, String pantryItemId, String itemName,
                  String category, int quantity, String expiryDate,
                  String datePurchased) {
        this.userId = userId;
        this.pantryItemId = pantryItemId;
        this.itemName = itemName;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
//        this.isExpired = isExpired;
        this.datePurchased = datePurchased;
        this.category = category;

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

//    public boolean isExpired() {
//        return isExpired;
//    }

    public String getDatePurchased() {
        return datePurchased;
    }

    public String getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }

// public String getCategory() {
     //   return category;
   // }

   // public Date getDateUsed() {
     //   return dateUsed;
    //}
}
