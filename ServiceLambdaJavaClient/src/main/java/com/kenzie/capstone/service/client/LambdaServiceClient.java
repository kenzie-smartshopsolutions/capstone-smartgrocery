package com.kenzie.capstone.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.capstone.service.model.ExampleData;
import com.kenzie.capstone.service.model.PantryData;
import com.kenzie.capstone.service.model.UserData;


public class LambdaServiceClient {

    private static final String GET_EXAMPLE_ENDPOINT = "example/{id}";
    private static final String SET_EXAMPLE_ENDPOINT = "example";

    private static final String GET_USER_ENDPOINT = "User/register/userId/{userId}";
    private static final String SET_USER_ENDPOINT = "User/register";

    private static final String GET_PANTRY_ENDPOINT = "Pantry/{pantryItemId}";
    private static final String SET_PANTRY_ENDPOINT = "Pantry/{pantryItemId}";

    private ObjectMapper mapper;

    public LambdaServiceClient() {
        this.mapper = new ObjectMapper();
    }

    public ExampleData getExampleData(String id) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_EXAMPLE_ENDPOINT.replace("{id}", id));
        ExampleData exampleData;
        try {
            exampleData = mapper.readValue(response, ExampleData.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return exampleData;
    }

    public ExampleData setExampleData(String data) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.postEndpoint(SET_EXAMPLE_ENDPOINT, data);
        ExampleData exampleData;
        try {
            exampleData = mapper.readValue(response, ExampleData.class);
        } catch (Exception e) {
            throw new ApiGatewayException("Unable to map deserialize JSON: " + e);
        }
        return exampleData;
    }

    public UserData getUserData(String userId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_USER_ENDPOINT.replace("{userId}", userId));
        UserData userData;
        try {
            userData = mapper.readValue(response, UserData.class);
        } catch (JsonProcessingException e) {
        // If there is an issue with deserializing the response, handle it appropriately
            throw new ApiGatewayException("Unable to deserialize JSON response: " + e.getMessage());
        } catch (ApiGatewayException e) {
        // If an ApiGatewayException is thrown, re-throw it
            throw e;
        } catch (Exception e) {
        // For other exceptions, handle them and wrap them in an ApiGatewayException
            throw new ApiGatewayException("An error occurred while processing the response: " + e.getMessage());
        }
        return userData;
    }

    public UserData setUserData(UserData data) {
        EndpointUtility endpointUtility = new EndpointUtility();
        try {
            // Convert UserData object to JSON string
            String jsonData = mapper.writeValueAsString(data);

            // Send JSON data to Lambda endpoint
            String response = endpointUtility.postEndpoint(SET_USER_ENDPOINT, jsonData);

            return mapper.readValue(response, UserData.class);
        } catch (JsonProcessingException e) {
        // If there is an issue with deserializing the response, handle it appropriately
            throw new ApiGatewayException("Unable to deserialize JSON response: " + e.getMessage());
        } catch (ApiGatewayException e) {
        // If an ApiGatewayException is thrown, re-throw it
            throw e;
        } catch (Exception e) {
        // For other exceptions, handle them and wrap them in an ApiGatewayException
            throw new ApiGatewayException("An error occurred while processing the response: " + e.getMessage());
        }
    }

    //pantryId??
    public PantryData getPantryData(String pantryItemId) {
        EndpointUtility endpointUtility = new EndpointUtility();
        String response = endpointUtility.getEndpoint(GET_PANTRY_ENDPOINT.replace("{pantryItemId}", pantryItemId));
        PantryData pantryData;
        try {
            pantryData = mapper.readValue(response, PantryData.class);
        } catch (JsonProcessingException e) {
            // If there is an issue with deserializing the response, handle it appropriately
            throw new ApiGatewayException("Unable to deserialize JSON response: " + e.getMessage());
        } catch (ApiGatewayException e) {
            // If an ApiGatewayException is thrown, re-throw it
            throw e;
        } catch (Exception e) {
            // For other exceptions, handle them and wrap them in an ApiGatewayException
            throw new ApiGatewayException("An error occurred while processing the response: " + e.getMessage());
        }
        return pantryData;
    }

    public PantryData setPantryData(PantryData data) {
        EndpointUtility endpointUtility = new EndpointUtility();

        //String response = endpointUtility.postEndpoint(SET_PANTRY_ENDPOINT, data);
        PantryData pantryData;
        try {
            String jsonData = mapper.writeValueAsString(data);
            String response = endpointUtility.postEndpoint(SET_PANTRY_ENDPOINT, jsonData);
            return mapper.readValue(response, PantryData.class);

        } catch (JsonProcessingException e) {
            // If there is an issue with deserializing the response, handle it appropriately
            throw new ApiGatewayException("Unable to deserialize JSON response: " + e.getMessage());
        } catch (ApiGatewayException e) {
            // If an ApiGatewayException is thrown, re-throw it
            throw e;
        } catch (Exception e) {
            // For other exceptions, handle them and wrap them in an ApiGatewayException
            throw new ApiGatewayException("An error occurred while processing the response: " + e.getMessage());
        }
    }

}
