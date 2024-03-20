package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;
import java.util.Objects;
@DynamoDBTable(tableName = "Recipe")
public class PantryRecord {
    private  String pantryItemId;
    private  String itemName;
    private  String expiryDate;
    private  int quantity;
    private  boolean isExpired;
    private  Date datePurchased;
    @DynamoDBHashKey(attributeName = "ItemId")

    public String getPantryItemId() {
        return pantryItemId;
    }

    public void setPantryItemId(String pantryItemId) {
        this.pantryItemId = pantryItemId;
    }
    @DynamoDBAttribute(attributeName = "ItemName")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    @DynamoDBAttribute(attributeName = "ExpiryDate")
    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    @DynamoDBAttribute(attributeName = "Quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @DynamoDBAttribute(attributeName = "Expired")
    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }
    @DynamoDBAttribute(attributeName = "DatePurchased")
    public Date getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(Date datePurchased) {
        this.datePurchased = datePurchased;
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
