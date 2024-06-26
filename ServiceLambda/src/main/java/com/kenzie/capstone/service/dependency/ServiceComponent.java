package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.LambdaService;

import com.kenzie.capstone.service.PantryLambdaService;
import com.kenzie.capstone.service.RecipeLambdaService;
import com.kenzie.capstone.service.UserLambdaService;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Declares the dependency roots that Dagger will provide.
 */
@Singleton
@Component(modules = {DaoModule.class, ServiceModule.class})
public interface ServiceComponent {
    LambdaService provideLambdaService();
    UserLambdaService provideUserLambdaService();
    PantryLambdaService providePantryLambdaService();
    RecipeLambdaService provideRecipeLambdaService();
}
