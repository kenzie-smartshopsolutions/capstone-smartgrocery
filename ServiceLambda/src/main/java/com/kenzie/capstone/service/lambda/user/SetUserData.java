package com.kenzie.capstone.service.lambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kenzie.capstone.service.UserLambdaService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.dependency.ServiceComponent;
import com.kenzie.capstone.service.model.UserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SetUserData implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{
    static final Logger log = LogManager.getLogger();
    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        log.info(gson.toJson(input));

        ServiceComponent serviceComponent = DaggerServiceComponent.create();
        UserLambdaService userLambdaService = serviceComponent.provideUserLambdaService();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
                .withHeaders(headers);

        UserData userData = gson.fromJson(input.getBody(), UserData.class);
        if (userData == null || userData.getUsername() == null || userData.getUsername().isEmpty()) {
            return response.withStatusCode(400).withBody("User information is invalid");
        }
        log.info("User data: {}", userData);
        try {
            UserData savedUserData = userLambdaService.setUserData(userData);
            String output = gson.toJson(savedUserData);
            return response.withStatusCode(201).withBody(output);
        } catch (Exception e) {
            log.error("Error saving user data: ", e);
            return response.withStatusCode(500).withBody(gson.toJson(e.getMessage()));
        }
    }
}
