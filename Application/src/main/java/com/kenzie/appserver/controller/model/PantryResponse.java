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
}
