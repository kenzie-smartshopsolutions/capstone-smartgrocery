package com.kenzie.appserver.service.model;

import java.util.Date;
import java.util.List;

public class Pantry {
    private final String pantryItemId;
    private final String itemName;
    private final String expiryDate;
    private final int quantity;
    private final boolean isExpired;
    private final Date datePurchased;
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
