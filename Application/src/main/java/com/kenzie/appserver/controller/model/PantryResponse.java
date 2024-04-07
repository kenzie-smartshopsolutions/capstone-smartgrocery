package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PantryResponse {
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
        @JsonProperty("userId")
        private String userId;

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

        public void setCatagoryId(int catagoryId) {
                this.catagoryId = catagoryId;
        }

        public void setUserId(String userId) { this.userId = userId; }

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

        public String getUserId() {
                return userId;
        }
}
