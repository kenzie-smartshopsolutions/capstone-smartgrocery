package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class PantryUpdateRequest {

    @JsonProperty("pantryItemId")
    private String pantryItemId;
    @JsonProperty("itemName")
    private String itemName;
    @JsonProperty("expiryDate")
    private String expiryDate;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("isExpired")
    private boolean isExpired;
    @JsonProperty("datePurchased")
    private Date datePurchased;
    @JsonProperty("catagoryId")
    private int catagoryId;



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
