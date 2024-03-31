package com.kenzie.appserver.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@DynamoDBTable(tableName = "Pantry")
public class Pantry {
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
    @DynamoDBAttribute(attributeName = "catagoryId")
    private final int catagoryId;



    public Pantry(String pantryItemId, String itemName, String expiryDate, int quantity, boolean isExpired, Date datePurchased, int catagoryId) {
        this.pantryItemId = pantryItemId;
        this.itemName = itemName;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.isExpired = isExpired;
        this.datePurchased = datePurchased;
        this.catagoryId = catagoryId;
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

    public int getCatagoryId() {
        return catagoryId;
    }
}
