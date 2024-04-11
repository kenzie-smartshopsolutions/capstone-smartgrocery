package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class PantryResponse {
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonProperty("pantryItemId")
        private String pantryItemId;
        @JsonProperty("itemName")
        private String itemName;
        @JsonProperty("expiryDate")
        private String expiryDate;
        @JsonProperty("quantity")
        private int quantity;
//        @JsonIgnore
//        @JsonProperty("isExpired")
//        private boolean isExpired;
        @JsonProperty("datePurchased")
        private String datePurchased;
        @JsonProperty("category")
        private String category;
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

//        public void setIsExpired(boolean isExpired) {
//                this.isExpired = isExpired;
//        }

        public void setDatePurchased(String datePurchased) {
                this.datePurchased = datePurchased;
        }

        public void setCategory(String category) {
                this.category = category;
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

//        public boolean isExpired() {
//                return isExpired;
//        }

        public String getDatePurchased() {
                return datePurchased;
        }

        public String getCategory() {
                return category;
        }

        public String getUserId() {
                return userId;
        }
}
