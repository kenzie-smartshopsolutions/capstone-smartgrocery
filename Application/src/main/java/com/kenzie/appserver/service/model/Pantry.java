package com.kenzie.appserver.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class Pantry {

  /*pantryItemId (String)
itemName (String)
quantity (int)
expiryDate (String)
datePurchased (Date/time)
isExpired (boolean
dateUsed(Date/time)
category(String)
*/

    @Id
    @NotNull
    @DynamoDBHashKey(attributeName = "pantryItemId")
    private final String pantryItemId;
    @DynamoDBAttribute(attributeName = "itemName")
    private final String itemName;
    @DynamoDBAttribute(attributeName = "expiryDate")
    private final String expiryDate;
    @DynamoDBAttribute(attributeName = "quantity")
    private final int quantity;
    @DynamoDBAttribute(attributeName = "isExpired")
    private final boolean isExpired;
    @DynamoDBAttribute(attributeName = "datePurchased")
    private final Date datePurchased;
    @DynamoDBAttribute(attributeName = "userId")
    private final String userId;

    public final String category;

   // private final Date dateUsed;


    public Pantry(String pantryItemId, String itemName, String expiryDate, int quantity, boolean isExpired, Date datePurchased, String category, String userId) {
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
