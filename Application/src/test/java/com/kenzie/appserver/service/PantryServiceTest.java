package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.PantryRequest;
import com.kenzie.appserver.repositories.PantryRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.ExampleRecord;
import com.kenzie.appserver.repositories.model.PantryRecord;

import com.kenzie.appserver.service.model.Example;
import com.kenzie.appserver.service.model.Pantry;
import com.kenzie.capstone.service.client.LambdaServiceClient;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

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
        String category = "Fruit";
        String userId = UUID.randomUUID().toString();


        Pantry expectedItem = new Pantry(pantryItemId,
                itemName, expiryDate, quanity, isExpired, datePurchased,category, userId);

        PantryRequest pantryRequest = new PantryRequest();
        pantryRequest.setPantryItemId(pantryItemId);
        pantryRequest.setItemName(itemName);
        pantryRequest.setExpiryDate(expiryDate);
        pantryRequest.setQuantity(quanity);
        pantryRequest.setExpired(isExpired);
        pantryRequest.setDatePurchased(datePurchased);
        pantryRequest.setCatagory(category);
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
    void findById() {
        // GIVEN
        String pantryItemId = UUID.randomUUID().toString();
        String itemName = "Banana";
        String expiryDate = "01/01/2025";
        int quanity = 1;
        boolean isExpired = false;
        Date datePurchased = new Date(4,8,24);
        String category = "Fruit";
        String userId = UUID.randomUUID().toString();

        PantryRequest request = new PantryRequest();
        request.setQuantity(quanity);
        request.setDatePurchased(datePurchased);
        request.setExpired(isExpired);
        request.setPantryItemId(pantryItemId);
        request.setExpiryDate(expiryDate);
        //record.setCategory(category);

        pantryService.addPantryItem(request);

        // WHEN
        List<PantryRecord> pantry = pantryService.getPantryItems(userId);
        when(pantryRepository.findByUserId(userId)).thenReturn(pantry);


        // THEN
        Assertions.assertNotNull(pantry, "The object is returned");
        //Assertions.assertTrue(pantry.));
        //Assertions.assertEquals(record.getName(), example.getName(), "The name matches");
    }

    @Test
    void findByUserId_invalid() {
        // GIVEN
        String invalidId = randomUUID().toString();
        //List<PantryRecord> list = null;

        when(pantryRepository.findByUserId(invalidId)).thenReturn(null);

        // WHEN
        List<PantryRecord> list = pantryService.getPantryItems(invalidId);

        // THEN
        Assertions.assertTrue(list.isEmpty());

    }
    @Test
    void deletePantryItemByItemId() {

        String pantryItemId = UUID.randomUUID().toString();
        String itemName = "Banana";
        String expiryDate = "01/01/2025";
        int quanity = 1;
        boolean isExpired = false;
        Date datePurchased = new Date(4,8,24);
        String category = "Fruit";
        String userId = UUID.randomUUID().toString();

//        PantryRecord record = new PantryRecord();
//        record.setCategory(category);
//        record.setPantryItemId(pantryItemId);
//        record.setExpired(isExpired);
//        record.setQuantity(quanity);
//        record.setExpiryDate(expiryDate);
//        record.setItemName(itemName);
//        record.setDatePurchased(datePurchased);
//        record.setUserId(userId);
//        pantryService.addPantryItem(record);
        PantryRequest request = new PantryRequest();
        request.setQuantity(quanity);
        request.setDatePurchased(datePurchased);
        request.setExpired(isExpired);
        request.setPantryItemId(pantryItemId);
        request.setExpiryDate(expiryDate);
        //record.setCategory(category);

        pantryService.addPantryItem(request);

            pantryService.deletePantryItem(pantryItemId);
            List<PantryRecord> list = pantryService.getPantryItems(userId);

            verify(pantryRepository).deleteById(pantryItemId);

    }

    @Test
    void updatePantryItem_isSuccessful() {
        String pantryItemId = UUID.randomUUID().toString();
        String itemName = "Banana";
        String expiryDate = "01/01/2025";
        int quanity = 1;
        boolean isExpired = false;
        Date datePurchased = new Date(4,8,24);
        String category = "Fruit";
        String userId = UUID.randomUUID().toString();

        PantryRequest request = new PantryRequest();
        request.setQuantity(quanity);
        request.setDatePurchased(datePurchased);
        request.setExpired(isExpired);
        request.setPantryItemId(pantryItemId);
        request.setExpiryDate(expiryDate);
        //record.setCategory(category);

        PantryRecord record = pantryService.addPantryItem(request);

        pantryService.updatePantryItem(record);
    }
    @Test
    public void testDeletePantryItem_sadCase_repositoryThrowsException() {
        String pantryItemId = "someId";
        RuntimeException expectedException = new RuntimeException("Something went wrong!");
        doThrow(expectedException).when(pantryRepository).deleteById(pantryItemId);

        assertThrows(RuntimeException.class, () -> pantryService.deletePantryItem(pantryItemId));

        // Verify  pet repository's delete method was called
        verify(pantryRepository).deleteById(pantryItemId);
    }





}
