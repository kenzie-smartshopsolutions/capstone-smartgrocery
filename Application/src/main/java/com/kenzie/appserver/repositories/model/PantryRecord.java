package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
@DynamoDBTable(tableName = "Pantry")
public class PantryRecord {
    private String userId;
    private  String pantryItemId;
    @NotNull(message = "Item name cannot be null")
    private  String itemName;
    @DynamoDBAttribute(attributeName = "Category")
    public String category;

    private  String expiryDate;
    private  int quantity;
    private  boolean isExpired;
    private  Date datePurchased;

    public PantryRecord() {
        this.userId = userId;
        this.pantryItemId = pantryItemId;
        this.itemName = itemName;
        this.category = category;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.isExpired = isExpired;
        this.datePurchased = datePurchased;

    }
    @DynamoDBHashKey(attributeName = "UserId")
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    @DynamoDBRangeKey(attributeName = "ItemId")
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
