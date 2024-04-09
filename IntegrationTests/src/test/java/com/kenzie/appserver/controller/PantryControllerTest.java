package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.PantryRequest;
import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.service.PantryService;
import net.andreinc.mockneat.MockNeat;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.apache.http.client.methods.RequestBuilder.post;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


    @IntegrationTest
    class PantryControllerTest {
        private static final MediaType JSON = MediaType.APPLICATION_JSON;

        @Autowired
        private MockMvc mvc;
        @Autowired
        private PantryService pantryService;
        private MockNeat mockNeat;
        private ObjectMapper mapper;

        @BeforeEach
        public void setUp() {
            mockNeat = MockNeat.threadLocal();
            mapper = createObjectMapper();
        }

        private ObjectMapper createObjectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper;
        }

        @Test
        public void getById_Exists() throws Exception {
           String userId = "123456";
           String pantryItemId = UUID.randomUUID().toString();
           String itemName = "Milk";
           String catagory = "Dairy";
            //Date datePurchased =


            PantryRequest request = new PantryRequest();
            request.setUserId(userId);
            request.setPantryItemId(pantryItemId);
            request.setItemName(itemName);
            request.setCatagory(catagory);

            PantryRecord persistedPantry = pantryService.addPantryItem(request);
            mvc.perform(get("/Pantry/{userId}", persistedPantry.getPantryItemId())
                            .accept(JSON))
                    .andExpect(jsonPath("userId")
                            .value(is(userId)))
                    .andExpect(jsonPath("pantryItemId")
                            .value(is(pantryItemId)))
                    .andExpect(status().isOk());
        }

        @Test
        public void testDeletePetById_Success() throws Exception{

        String pantryItemId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        PantryRequest pantryRequest = new PantryRequest();
        pantryRequest.setUserId(userId);
        pantryRequest.setPantryItemId(pantryItemId);

        mapper.registerModule(new JavaTimeModule());

            PantryRecord pantryRecord = pantryService.addPantryItem(pantryRequest);

            //WHEN
            mvc.perform(delete("/Pantry/{pantryItemId}/", pantryRecord.getPantryItemId())
                            .accept(MediaType.APPLICATION_JSON))
                    // THEN
                    .andExpect(status().isNoContent());

            //assertThat(petService.findByPetId(id)).isNull();

        }
        @Test
        public void testGetPantryDetailsByItemId_Success() throws Exception {

            String userId = UUID.randomUUID().toString();
            String pantryItemId = UUID.randomUUID().toString();
            String itemName = "Milk";
            String catagory = "Dairy";

            PantryRequest request = new PantryRequest();
            request.setPantryItemId(pantryItemId);
            request.setUserId(userId);
            request.setItemName(itemName);
            request.setCatagory(catagory);

            PantryRecord pantryRecord = pantryService.addPantryItem(request);

            mapper.registerModule(new JavaTimeModule());

            assertEquals(pantryRecord.getCategory(), catagory);
            assertEquals(pantryRecord.getItemName(), itemName);

            mvc.perform(
                            get("/Pantry/{pantryItemId}", pantryRecord.getPantryItemId())
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("catagory").value(is(catagory)))
                    .andExpect(jsonPath("itemName").value(is(itemName)));
        }
        @Test
        public void testGetPantryListByUserId_Success() throws Exception {

            String userId = UUID.randomUUID().toString();
            String pantryItemId = UUID.randomUUID().toString();
            String itemName = "Milk";
            String catagory = "Dairy";

            PantryRequest request = new PantryRequest();
            request.setPantryItemId(pantryItemId);
            request.setUserId(userId);
            request.setItemName(itemName);
            request.setCatagory(catagory);

            PantryRecord pantryRecord = pantryService.addPantryItem(request);

            String pantryItemId2 = UUID.randomUUID().toString();
            String itemName2 = "Banana";
            String catagory2 = "Fruit";

            PantryRequest request1 = new PantryRequest();
            request1.setCatagory(catagory2);
            request1.setUserId(userId);
            request1.setPantryItemId(pantryItemId2);
            request1.setItemName(itemName2);

            PantryRecord pantryRecord1 = pantryService.addPantryItem(request1);

            List<PantryRecord> pantryList = new ArrayList<>();
            pantryList.add(pantryRecord1);
            pantryList.add(pantryRecord);

            mapper.registerModule(new JavaTimeModule());

//            assertEquals(pantryRecord.getCategory(), catagory);
//            assertEquals(pantryRecord.getItemName(), itemName);
            assertTrue(pantryList.contains(pantryRecord1));
            assertTrue(pantryList.contains(pantryRecord));

            mvc.perform(
                            get("/Pantry/{userId}", pantryRecord.getUserId())
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
//                    .andExpect(jsonPath("catagory").value(is(catagory)))
//                    .andExpect(jsonPath("itemName").value(is(itemName)));
        }

        @Test
        public void testSearchPantryByItemId_InvalidId() throws Exception {
            // GIVEN
            String invalidId = "invalidId";

            mvc.perform(get("/pantry/{pantryItemId}", invalidId)
                            .accept(MediaType.APPLICATION_JSON))
                    // THEN
                    .andExpect(status().isNotFound());


        }
        @Test
        public void testGetPantryDetailsByUserId_NonexistentUser() throws Exception {
            // GIVEN
            String id = UUID.randomUUID().toString();
            //WHEN
            mvc.perform(get("/pantry{userId}", id)
                            .accept(MediaType.APPLICATION_JSON))
                    // THEN
                    .andExpect(status().isNotFound());


        }
}
