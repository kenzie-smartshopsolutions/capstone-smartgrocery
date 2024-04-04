package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
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

    public UserData storeUserData(UserData userData) {
        try {
            mapper.save(userData, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "userId",
                            new ExpectedAttributeValue()
                                    .withValue(new AttributeValue().withS(userData.getUserId()))
                                    .withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("Account already exists for this userId: " + userData.getUserId());
        }
        return userData;
    }

    public UserRecord getUserData(String userId) {
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(userId);

        DynamoDBQueryExpression<UserRecord> queryExpression = new DynamoDBQueryExpression<UserRecord>()
                .withHashKeyValues(userRecord)
                .withConsistentRead(false);
        return mapper.query(UserRecord.class, queryExpression).get(0);
    }

    public UserRecord setUserData (String userId, UserData userData) {
        UserRecord userRecord = new UserRecord();
        if (userId == null || userId.isEmpty() || userId.isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        } else {
            userRecord.setUserId(userId);
        }
        UserRecord tempData = convertToUserRecord(userData);

        mapper.save(tempData);
        return userRecord;
    }

    // Convert between UserRecord (Entity) and UserData (DTO)
    public UserData convertToUserData(UserRecord userRecord) {
        if (userRecord == null) return null;
        return new UserData(userRecord.getUserId(),
                userRecord.getUsername(),
                userRecord.getEmail(),
                userRecord.getPassword(),
                userRecord.getHouseholdName());
    }

    public UserRecord convertToUserRecord(UserData userData) {
        return new UserRecord(userData.getUserId(),
                userData.getUsername(),
                userData.getEmail(),
                userData.getPassword(),
                userData.getHouseholdName());
    }
}
