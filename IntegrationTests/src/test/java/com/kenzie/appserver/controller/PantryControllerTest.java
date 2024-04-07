package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.PantryRequest;
import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.service.PantryService;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
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
            mvc.perform(get("/pantry{userId}", persistedPantry.getPantryItemId())
                            .accept(JSON))
                    .andExpect(jsonPath("userId")
                            .value(is(userId)))
                    .andExpect(jsonPath("pantryItemId")
                            .value(is(pantryItemId)))
                    .andExpect(status().isOk());
        }

//        @Test
//        public void testDeletePetById_Success() throws Exception{
//            // GIVEN
//            //create pantry?
//
//            //WHEN
//            mvc.perform(delete("/pantry/{userId}/", pantry.getUserId())
//                            .accept(MediaType.APPLICATION_JSON))
//                    // THEN
//                    .andExpect(status().isNoContent());
//
//
//        }
}
