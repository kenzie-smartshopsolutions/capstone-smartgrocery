package com.kenzie.capstone.service.lambda.pantry;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.PantryLambdaService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.PantryData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SetPantryData implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        log.info(gson.toJson(input));

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        PantryLambdaService pantryLambdaService = serviceComponent.providePantryLambdaService();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);


        PantryData pantryData = gson.fromJson(input.getBody(), PantryData.class);

        if (pantryData == null || pantryData.getPantryItemId()== null || pantryData.getPantryItemId().isEmpty()) {
            return response.withStatusCode(400).withBody("Pantry item does not exist");
        }
        log.info("Pantry data: {}", pantryData);
        try {
            PantryData savedPantryData = pantryLambdaService.setPantryData(pantryData);
            String output = gson.toJson(savedPantryData);
            return response
                    .withStatusCode(201)
                    .withBody(output);

        } catch (Exception e) {
//            return response
//                    .withStatusCode(400)
//                    .withBody(gson.toJson(e.getMessage()));
            log.error("Error saving user data: ", e);
            return response.withStatusCode(500).withBody(gson.toJson(e.getMessage()));

        }
    }
}
