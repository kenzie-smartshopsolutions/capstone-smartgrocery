package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PantryUpdateRequest {
    @JsonIgnoreProperties(ignoreUnknown = true)

        @JsonProperty("pantryItemId")
        private String pantryItemId;
        @JsonProperty("itemName")
        private String itemName;
        @JsonProperty("expiryDate")
        private String expiryDate;
        @JsonProperty("quantity")
        private int quantity;
        @JsonIgnore
        @JsonProperty("isExpired")
        private boolean isExpired;
        @JsonProperty("datePurchased")
        private String datePurchased;
        @JsonProperty("userId")
        private String userId;
        @JsonProperty("category")
        private String category;



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

        public String getDatePurchased() {
            return datePurchased;
        }


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

        public void setIsExpired(boolean isExpired) {
            this.isExpired = isExpired;
        }

        public void setDatePurchased(String datePurchased) {
            this.datePurchased = datePurchased;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }


