package com.kenzie.capstone.service.lambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.kenzie.capstone.service.UserLambdaService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import com.kenzie.capstone.service.model.UserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateUserData implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    static final Logger log = LogManager.getLogger();
    private final Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        UserLambdaService userLambdaService = DaggerServiceComponent.create().provideUserLambdaService();
        UserData userData = gson.fromJson(request.getBody(), UserData.class);

        try {
            UserData updatedUserData = userLambdaService.updateUserData(userData);
            String output = gson.toJson(updatedUserData);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody(output);
        } catch (Exception e) {
            log.error(e);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody(gson.toJson(e.getMessage()));
        }
    }
}
