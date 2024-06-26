package com.kenzie.capstone.service.dependency;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.dao.PantryDao;
import com.kenzie.capstone.service.dao.RecipeDao;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.util.DynamoDbClientProvider;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Provides DynamoDBMapper instance to DAO classes.
 */
@Module
public class DaoModule {

    @Singleton
    @Provides
    @Named("DynamoDBMapper")
    public DynamoDBMapper provideDynamoDBMapper() {
        return new DynamoDBMapper(DynamoDbClientProvider.getDynamoDBClient());
    }

    @Singleton
    @Provides
    @Named("ExampleDao")
    @Inject
    public ExampleDao provideExampleDao(@Named("DynamoDBMapper") DynamoDBMapper mapper) {
        return new ExampleDao(mapper);
    }
    @Singleton
    @Provides
    @Named("UserDao")
    @Inject
    public UserDao provideUserDao(@Named("DynamoDBMapper") DynamoDBMapper mapper) {
        return new UserDao(mapper);
    }

    @Singleton
    @Provides
    @Named("PantryDao")
    @Inject
    public PantryDao providePantryDao(@Named("DynamoDBMapper") DynamoDBMapper mapper) {
        return new PantryDao(mapper);
    }

    @Singleton
    @Provides
    @Named("RecipeDao")
    @Inject
    public RecipeDao provideRecipeDao(@Named("DynamoDBMapper") DynamoDBMapper mapper) {
        return new RecipeDao(mapper);
    }
}
