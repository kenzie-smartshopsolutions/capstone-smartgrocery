package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.LambdaService;
import com.kenzie.capstone.service.PantryLambdaService;
import com.kenzie.capstone.service.UserLambdaService;
import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.dao.PantryDao;
import com.kenzie.capstone.service.dao.UserDao;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Module(
    includes = DaoModule.class
)
public class ServiceModule {

    @Singleton
    @Provides
    @Inject
    public LambdaService provideLambdaService(@Named("ExampleDao") ExampleDao exampleDao) {
        return new LambdaService(exampleDao);
    }

    @Singleton
    @Provides
    @Inject
    public UserLambdaService provideUserLambdaService(@Named("UserDao") UserDao userDao) {
        return new UserLambdaService(userDao);
    }

    @Singleton
    @Provides
    @Inject
    public PantryLambdaService providePantryLambdaService(@Named("PantryDao") PantryDao pantryDao) {
        return new PantryLambdaService(pantryDao);
    }
}

