package com.kenzie.capstone.service.lambda.Recipe;

import com.kenzie.capstone.service.LambdaService;
import com.kenzie.capstone.service.RecipeLambdaService;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.RecipeData;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class GetRecipeData implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        log.info(gson.toJson(input));

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        RecipeLambdaService recipeLambdaService = serviceComponent.provideRecipeLambdaService();

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        String recipeId = input.getPathParameters().get("recipeId");

        if (recipeId == null || recipeId.isEmpty()) {
            return response
                    .withStatusCode(400)
                    .withBody("Recipe ID is invalid");
        }

        try {
            //???
            RecipeData recipeData = RecipeLambdaService.getRecipeData(recipeId);
            String output = gson.toJson(recipeData);

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
