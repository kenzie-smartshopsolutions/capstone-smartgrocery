package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class PantryRequest {

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
//    @JsonProperty("catagoryId")
//    private int catagoryId;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("catagory")
    private String catagory;



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

//
//    //public int getCatagoryId() {
//        return catagoryId;
//    }

    public void setPantryItemId(String pantryItemId) {
        this.pantryItemId = pantryItemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public void setDatePurchased(Date datePurchased) {
        this.datePurchased = datePurchased;
    }

//    public void setCatagoryId(int catagoryId) {
//        this.catagoryId = catagoryId;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }
}
