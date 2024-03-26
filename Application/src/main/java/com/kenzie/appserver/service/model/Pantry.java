package com.kenzie.appserver.service.model;

import java.util.Date;

public class Pantry {
    /*pantryItemId (String)
itemName (String)
quantity (int)
expiryDate (String)
datePurchased (Date/time)
isExpired (boolean
*/

    private final String pantryItemId;
    private final String itemName;
    private final String expiryDate;

    private final int quantity;
    private final boolean isExpired;
    private final Date datePurchased;


    public Pantry(String pantryItemId, String itemName, String expiryDate, int quantity, boolean isExpired, Date datePurchased) {
        this.pantryItemId = pantryItemId;
        this.itemName = itemName;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.isExpired = isExpired;
        this.datePurchased = datePurchased;
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
}
