package com.kenzie.capstone.service.lambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.kenzie.capstone.service.UserLambdaService;
import com.kenzie.capstone.service.dependency.DaggerServiceComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserData implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    static final Logger log = LogManager.getLogger();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        UserLambdaService userLambdaService = DaggerServiceComponent.create().provideUserLambdaService();
        String userId = request.getPathParameters().get("userId");

        try {
            userLambdaService.deleteUserData(userId);
            return new APIGatewayProxyResponseEvent().withStatusCode(204);
        } catch (Exception e) {
            log.error("Error deleting user data: ", e);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("{\"error\":\"Internal Server Error\"}");
        }
    }
}
