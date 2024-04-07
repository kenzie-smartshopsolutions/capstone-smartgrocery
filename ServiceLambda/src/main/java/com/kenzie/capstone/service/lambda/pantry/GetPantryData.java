package com.kenzie.capstone.service.lambda.pantry;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.LambdaService;
import com.kenzie.capstone.service.PantryLambdaService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.PantryData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class GetPantryData implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

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

//        String id = input.getPathParameters().get("id");
//
//        if (id == null || id.length() == 0) {
//            return response
//                    .withStatusCode(400)
//                    .withBody("Id is invalid");
//        }
        //userId to get pantry list
        String pantryItemId = input.getPathParameters().get("pantryItemId");
        if (pantryItemId == null || pantryItemId.length() == 0) {
            return response
                    .withStatusCode(400)
                    .withBody("Pantry item is invalid");
        }

        try {
            PantryData pantryData = pantryLambdaService.getPantryData(pantryItemId);
            String output = gson.toJson(pantryData);

            return response
                    .withStatusCode(200)
                    .withBody(output);

        } catch (Exception e) {
            return response
                    .withStatusCode(400)
                    .withBody(gson.toJson(e.getMessage()));
        }
    }
}
