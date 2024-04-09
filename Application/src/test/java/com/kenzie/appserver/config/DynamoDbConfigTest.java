package com.kenzie.appserver.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = DynamoDbConfig.class)
@TestPropertySource(properties = {
        "dynamodb.override_endpoint=true",
        "dynamodb.endpoint=http://localhost:8000"
})
public class DynamoDbConfigTest {

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Test
    void amazonDynamoDBBeanIsNotNull() {
        assertNotNull(amazonDynamoDB);
    }
}

