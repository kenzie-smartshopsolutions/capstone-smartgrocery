package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;
import java.util.Objects;
@DynamoDBTable(tableName = "Ingredient")
public class IngredientRecord {
    private  String ingredientId;
    private  String ingredientName;
    private  double price;
    private  Date datePriced;
    private  int quantity;
    @DynamoDBHashKey(attributeName = "IngredientId")
    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }
    @DynamoDBAttribute(attributeName = "IngredientName")
    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
    @DynamoDBAttribute(attributeName = "Price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @DynamoDBAttribute(attributeName = "DatePriced")
    public Date getDatePriced() {
        return datePriced;
    }

    public void setDatePriced(Date datePriced) {
        this.datePriced = datePriced;
    }
    @DynamoDBAttribute(attributeName = "Quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientRecord that = (IngredientRecord) o;
        return Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId);
    }
}
