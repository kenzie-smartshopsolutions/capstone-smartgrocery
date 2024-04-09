package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.RecipeDao;
import dagger.internal.Factory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class RecipeLambdaService_Factory implements Factory<RecipeLambdaService> {
  private final Provider<RecipeDao> recipeDaoProvider;

  public RecipeLambdaService_Factory(Provider<RecipeDao> recipeDaoProvider) {
    this.recipeDaoProvider = recipeDaoProvider;
  }

  @Override
  public RecipeLambdaService get() {
    return newInstance(recipeDaoProvider.get());
  }

  public static RecipeLambdaService_Factory create(Provider<RecipeDao> recipeDaoProvider) {
    return new RecipeLambdaService_Factory(recipeDaoProvider);
  }

  public static RecipeLambdaService newInstance(RecipeDao recipeDao) {
    return new RecipeLambdaService(recipeDao);
  }
}
