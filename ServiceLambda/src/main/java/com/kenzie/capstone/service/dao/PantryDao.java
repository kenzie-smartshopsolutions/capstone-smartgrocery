package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import com.kenzie.capstone.service.model.PantryData;
import com.kenzie.capstone.service.model.PantryRecord;
import com.kenzie.capstone.service.model.UserRecord;

import java.util.List;
import java.util.UUID;

public class PantryDao {
    private DynamoDBMapper mapper;

    /**
     * Allows access to and manipulation of Match objects from the data store.
     * @param mapper Access to DynamoDB
     */
    public PantryDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }


    public PantryRecord storePantryRecord(PantryRecord pantryRecord) {
        try {
            mapper.save(pantryRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "userId",
                            new ExpectedAttributeValue()
                                    .withValue(new AttributeValue().withS(pantryRecord.getUserId()))
                                    .withExists(false))));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("Account already exists for this userId: " + pantryRecord.getUserId());
        }
        return pantryRecord;
    }

    public PantryRecord getPantryRecord(String pantryItemId) {
//        PantryRecord pantryRecord = mapper.load(PantryRecord.class, pantryItemId);
//        return pantryRecord;
        PantryRecord pantryRecord = new PantryRecord();
        if (pantryItemId == null || pantryItemId.isEmpty() || pantryItemId.isBlank()) {
            throw new IllegalArgumentException("PantryItemId cannot be blank or null");
        } else {
            pantryRecord.setPantryItemId(pantryItemId);
        }
        DynamoDBQueryExpression<PantryRecord> queryExpression = new DynamoDBQueryExpression<PantryRecord>()
                .withHashKeyValues(pantryRecord)
                .withConsistentRead(false);
        return mapper.query(PantryRecord.class, queryExpression).get(0);
    }

    public List<PantryRecord> getPantryRecordsByUserId(String userId) {
        PantryRecord pantryRecord = new PantryRecord();
        pantryRecord.setUserId(userId);
        DynamoDBQueryExpression<PantryRecord> queryExpression = new DynamoDBQueryExpression<PantryRecord>()
                .withHashKeyValues(pantryRecord)
                .withIndexName("userIdIndex")
                .withConsistentRead(false);
        return mapper.query(PantryRecord.class, queryExpression);

    }

    public PantryRecord updatePantryRecord(PantryRecord pantryRecord) {
        if (pantryRecord.getUserId() == null || pantryRecord.getUserId().isEmpty() || pantryRecord.getUserId().isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }
        mapper.save(pantryRecord);
        return pantryRecord;
    }


    public PantryData convertToPantryData(PantryRecord pantryRecord) {
        if (pantryRecord == null) return null;
        return new PantryData(   pantryRecord.getUserId(),
                pantryRecord.getPantryItemId(),
                pantryRecord.getItemName(),
                pantryRecord.getCategory(),
                pantryRecord.getQuantity(),
                pantryRecord.getExpiryDate(),
//                pantryRecord.isExpired(),
                pantryRecord.getDatePurchased());
    }

    public PantryRecord convertToPantryRecord(PantryData pantryData) {
        return new PantryRecord(pantryData.getUserId(),
                pantryData.getPantryItemId(),
                pantryData.getItemName(),
                pantryData.getCategory(),
                pantryData.getQuantity(),
                pantryData.getExpiryDate(),
//                pantryData.isExpired(),
                pantryData.getDatePurchased());
    }
    public PantryRecord setPantryData(String pantryItemId, PantryData pantryData) {
        PantryRecord pantryRecord = new PantryRecord();
        if (pantryItemId == null || pantryItemId.isEmpty() || pantryItemId.isBlank()) {
            String uniquePantryId = UUID.randomUUID().toString();
            pantryRecord.setPantryItemId(uniquePantryId);
        }
        PantryRecord tempData = convertToPantryRecord(pantryData);

        mapper.save(tempData);
        return pantryRecord;
    }
    }

