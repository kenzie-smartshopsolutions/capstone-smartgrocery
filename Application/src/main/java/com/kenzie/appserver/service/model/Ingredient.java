package com.kenzie.appserver.service.model;

import java.util.Date;
import java.util.List;

public class Ingredient {
    /*ingredientId (String)
ingredientName (String)
price (double) [output formatting needed]
quantity (integer)
datePriced (date/time)
*/

    private final String ingredientId;
    private final String ingredientName;
    private final double price;
    private final Date datePriced;
    private final int quantity;

    public Ingredient(String ingredientName, String ingredientId, double price, Date datePriced, int quantity) {
        this.ingredientName = ingredientName;
        this.ingredientId = ingredientId;
        this.price = price;
        this.datePriced = datePriced;
        this.quantity = quantity;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public double getPrice() {
        return price;
    }

    public Date getDatePriced() {
        return datePriced;
    }

    public int getQuantity() {
        return quantity;
    }
}
