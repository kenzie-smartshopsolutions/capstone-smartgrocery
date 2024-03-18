package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.UserData;
import com.kenzie.capstone.service.model.UserRecord;

public class UserDao {
    private DynamoDBMapper mapper;
    /**
     * Allows access to and manipulation of Match objects from the data store.
     * @param mapper Access to DynamoDB
     */

    public UserDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    public UserRecord storeUserRecord(UserRecord userRecord) {
        try {
            mapper.save(userRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "userId",
                            new ExpectedAttributeValue()
                                    .withValue(new AttributeValue().withS(userRecord.getUserId()))
                                    .withExists(false))));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("Account already exists for this userId: " + userRecord.getUserId());
        }
        return userRecord;
    }

    public UserRecord getUserRecord(String userId) {
        UserRecord userRecord = mapper.load(UserRecord.class, userId);
        return userRecord;
    }

    public UserRecord updateUserRecord(UserRecord userRecord) {
        if (userRecord.getUserId() == null || userRecord.getUserId().isEmpty() || userRecord.getUserId().isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }
        mapper.save(userRecord);
        return userRecord;
    }

    // Convert between UserRecord (Entity) and UserData (DTO)
    public UserData convertToUserData(UserRecord userRecord) {
        if (userRecord == null) return null;
        return new UserData(userRecord.getUserId(),
                userRecord.getUsername(),
                userRecord.getEmail(),
                userRecord.getPasswordHash(),
                userRecord.getHouseholdName());
    }

    public UserRecord convertToUserRecord(UserData userData) {
        return new UserRecord(userData.getUserId(),
                userData.getUsername(),
                userData.getEmail(),
                userData.getPasswordHash(),
                userData.getHouseholdName());
    }
}
