package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.PantryRequest;
import com.kenzie.appserver.repositories.PantryRepository;
import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.PantryService;
import com.kenzie.appserver.service.model.Pantry;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.org.bouncycastle.jcajce.provider.asymmetric.ec.KeyFactorySpi;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testcontainers.shaded.org.hamcrest.Matchers.any;


@IntegrationTest
    class PantryControllerTest {
        private static final MediaType JSON = MediaType.APPLICATION_JSON;

        @Autowired
        private MockMvc mvc;
        @Autowired
        private PantryService pantryService;
        @Mock
        private PantryRepository pantryRepository;
        private MockNeat mockNeat;
        private ObjectMapper mapper;
        private Pantry samplePantryItem;

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

        ////////Tests for GET////////

        @Test
        public void testGetPantryListByUserId_Success() throws Exception {

            String userId = UUID.randomUUID().toString();
            String pantryItemId = UUID.randomUUID().toString();
            String itemName = "Milk";
            String category = "Dairy";

            PantryRequest request = new PantryRequest();
            request.setUserId(userId);
            request.setItemName(itemName);
            request.setCategory(category);

            PantryRecord pantryRecord = pantryService.addPantryItem(request);

            String pantryItemId2 = UUID.randomUUID().toString();
            String itemName2 = "Banana";
            String category2 = "Fruit";

            PantryRequest request1 = new PantryRequest();
            request1.setCategory(category2);
            request1.setUserId(userId);

            request1.setItemName(itemName2);

            PantryRecord pantryRecord1 = pantryService.addPantryItem(request1);

            List<PantryRecord> pantryList = new ArrayList<>();
            pantryList.add(pantryRecord1);
            pantryList.add(pantryRecord);

            mapper.registerModule(new JavaTimeModule());

//            assertEquals(pantryRecord.getCategory(), category);
//            assertEquals(pantryRecord.getItemName(), itemName);
            assertTrue(pantryList.contains(pantryRecord1));
            assertTrue(pantryList.contains(pantryRecord));

            mvc.perform(
                            get("/Pantry/{userId}", pantryRecord.getUserId())
                                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
//                    .andExpect(jsonPath("category").value(is(category)))
//                    .andExpect(jsonPath("itemName").value(is(itemName)));
        }

        @Test

        public void testGetPantryDetailsByUserId_NonexistentUser() throws Exception {
            // GIVEN
            String id = UUID.randomUUID().toString();
            //WHEN

            mvc.perform(get("/pantry/{userId}", id)

                            .accept(MediaType.APPLICATION_JSON))
                    // THEN
                    .andExpect(status().isNotFound());


        }

        ////////Tests for DELETE////////
        @Test
        public void testDeletePantryItemById_Success() throws Exception{

            String pantryItemId = UUID.randomUUID().toString();
            String userId = UUID.randomUUID().toString();

            PantryRequest pantryRequest = new PantryRequest();
            pantryRequest.setUserId(userId);


            mapper.registerModule(new JavaTimeModule());

            PantryRecord pantryRecord = pantryService.addPantryItem(pantryRequest);

            //WHEN
            mvc.perform(delete("/Pantry/pantryItemId/{pantryItemId}/", pantryRecord.getPantryItemId())
                            .accept(MediaType.APPLICATION_JSON))
                    // THEN
                    .andExpect(status().isNoContent());

            //assertThat(petService.findByPetId(id)).isNull();

        }
        @Test
        void deletePantryItemTest() throws Exception {
            mvc.perform(delete("/pantry/{pantryItemId}", samplePantryItem.getPantryItemId()))
                    .andExpect(status().isNoContent());
        }
        @Test
        void deletePantryItem_IdNotFountTest() throws Exception {
            String invalidId = null;

            mvc.perform(delete("/Pantry/pantryItemId/{pantryItemId}/", invalidId)
                            .accept(MediaType.APPLICATION_JSON))
                    // THEN
                    .andExpect(status().isNoContent());
        }

        ////////Tests for POST////////
        @Test
        void addPantryItemTest() throws Exception {
            String userId = UUID.randomUUID().toString();
            String pantryItemId = UUID.randomUUID().toString();
            String itemName = "Milk";
            String category = "Dairy";

            PantryRequest request = new PantryRequest();

            request.setUserId(userId);
            request.setItemName(itemName);
            request.setCategory(category);

            PantryRecord record = new PantryRecord();
            record.setUserId(userId);
            record.setPantryItemId(pantryItemId);
            record.setItemName(itemName);
            record.setCategory(category);

            //when(pantryRepository.save(any(PantryRecord.class))).thenReturn(record);


            //when

            PantryRecord createdItem = pantryService.addPantryItem(request);
            //then
            assertThat(createdItem).isNotNull();
            assertThat(createdItem.getItemName()).isEqualTo(record.getItemName());
            assertThat(createdItem.getPantryItemId()).isEqualTo(record.getPantryItemId());
            assertThat(createdItem.getUserId()).isEqualTo(record.getUserId());
            assertThat(createdItem.getCategory()).isEqualTo(record.getCategory());

        }
    ////////Tests for PUT////////
    @Test
    void updatePantryItemTest() throws Exception {
            PantryRecord pantryRecord = pantryService.convertFromDto(samplePantryItem);

            given(pantryService.updatePantryItem(pantryRecord)).willReturn(pantryRecord);

            mvc.perform(put("/Pantry/pantryItemId/{pantryItemId}", pantryRecord.getPantryItemId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(samplePantryItem)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.itemName").value(samplePantryItem.getItemName()));

    }





        ////////Tests for GET by item id////////
//        @Test
//        public void testGetPantryDetailsByItemId_Success() throws Exception {
//
//            String userId = UUID.randomUUID().toString();
//            String pantryItemId = UUID.randomUUID().toString();
//            String itemName = "Milk";
//            String category = "Dairy";
//
//            PantryRequest request = new PantryRequest();
//            request.setPantryItemId(pantryItemId);
//            request.setUserId(userId);
//            request.setItemName(itemName);
//            request.setcategory(category);
//
//            PantryRecord pantryRecord = pantryService.addPantryItem(request);
//
//            mapper.registerModule(new JavaTimeModule());
//
//            assertEquals(pantryRecord.getCategory(), category);
//            assertEquals(pantryRecord.getItemName(), itemName);
//
//            mvc.perform(
//                            get("/Pantry/{pantryItemId}", pantryRecord.getPantryItemId())
//                                    .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("category").value(is(category)))
//                    .andExpect(jsonPath("itemName").value(is(itemName)));
//        }

 //       @Test
//        public void getById_Exists() throws Exception {
//           String userId = "123456";
//           String pantryItemId = UUID.randomUUID().toString();
//           String itemName = "Milk";
//           String category = "Dairy";
//            //Date datePurchased =
//
//
//            PantryRequest request = new PantryRequest();
//            request.setUserId(userId);
//            request.setPantryItemId(pantryItemId);
//            request.setItemName(itemName);
//            request.setcategory(category);
//
//            PantryRecord persistedPantry = pantryService.addPantryItem(request);
//            mvc.perform(get("/Pantry/{userId}", persistedPantry.getPantryItemId())
//                            .accept(JSON))
//                    .andExpect(jsonPath("userId")
//                            .value(is(userId)))
//                    .andExpect(jsonPath("pantryItemId")
//                            .value(is(pantryItemId)))
//                    .andExpect(status().isOk());
//        }

//        @Test
//        public void testSearchPantryByItemId_InvalidId() throws Exception {
//            // GIVEN
//            String invalidId = "invalidId";
//
//            mvc.perform(get("/pantry/{pantryItemId}", invalidId)
//                            .accept(MediaType.APPLICATION_JSON))
//                    // THEN
//                    .andExpect(status().isNotFound());
//
//
//        }





}
