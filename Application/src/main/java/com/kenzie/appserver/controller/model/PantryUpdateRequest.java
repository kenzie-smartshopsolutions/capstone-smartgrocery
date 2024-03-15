package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class PantryUpdateRequest {
    @NotEmpty
    @JsonProperty("pantryItemId")
    private String pantryItemId;
    @NotEmpty
    @JsonProperty("itemName")
    private String itemName;
    @NotEmpty
    @JsonProperty("expiryDate")
    private String expiryDate;
    @NotEmpty
    @JsonProperty("quantity")
    private int quantity;
    @NotEmpty
    @JsonProperty("isExpired")
    private boolean isExpired;
    @NotEmpty
    @JsonProperty("datePurchased")
    private Date datePurchased;
    @NotEmpty
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
