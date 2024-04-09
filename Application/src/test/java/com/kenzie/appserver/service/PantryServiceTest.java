package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.PantryRequest;
import com.kenzie.appserver.repositories.PantryRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.Pantry;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PantryServiceTest {

    @Mock
    PantryRepository pantryRepository;
    @Mock
    LambdaServiceClient lambdaServiceClient;
    PantryService pantryService;


    @BeforeEach
    void initiate() {
        MockitoAnnotations.initMocks(this);
        pantryService = new PantryService(pantryRepository,  lambdaServiceClient);
    }

    @Test
    void addNewPantryItem_validData() {
        //given
        String pantryItemId = UUID.randomUUID().toString();
        String itemName = "Banana";
        String expiryDate = "01/01/2025";
        int quanity = 1;
        boolean isExpired = false;
        Date datePurchased = new Date(4,8,24);
        String catagory = "Fruit";
        String userId = UUID.randomUUID().toString();


        Pantry expectedItem = new Pantry(pantryItemId,
                itemName, expiryDate, quanity, isExpired, datePurchased,catagory, userId);

        PantryRequest pantryRequest = new PantryRequest();
        pantryRequest.setPantryItemId(pantryItemId);
        pantryRequest.setItemName(itemName);
        pantryRequest.setExpiryDate(expiryDate);
        pantryRequest.setQuantity(quanity);
        pantryRequest.setExpired(isExpired);
        pantryRequest.setDatePurchased(datePurchased);
        pantryRequest.setCatagory(catagory);
        pantryRequest.setUserId(userId);

        // setup save method to return your expectedPet object
        //when(pantryRepository.save(any(Pantry.class))).thenReturn(expectedItem);

        //when
        PantryRecord createditem = pantryService.addPantryItem(pantryRequest);
        //then
        assertThat(createditem).isNotNull();
        assertThat(createditem.getItemName()).isEqualTo(expectedItem.getItemName());
        assertThat(createditem.getPantryItemId()).isEqualTo(expectedItem.getPantryItemId());
        assertThat(createditem.getCategory()).isEqualTo(expectedItem.getCategory());
        assertThat(createditem.getQuantity()).isEqualTo(expectedItem.getQuantity());
        assertThat(createditem.getDatePurchased()).isEqualTo(expectedItem.getDatePurchased());
        assertThat(createditem.getUserId()).isEqualTo(expectedItem.getUserId());
    }
    @Test
    void getPantryByUserIdTest() {

    }

    @Test
    void getPantryByUserIdWithInvalidUserIdFails() {

    }

    @Test
    void deletePantryItemByItemId() {
            String pantryItemId = "testPantryId";

            pantryService.deletePantryItem(pantryItemId);

            verify(pantryRepository).deleteById(pantryItemId);
    }


}
