package com.kenzie.capstone.service.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.capstone.service.dao.RecipeDao;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DaoModule_ProvideRecipeDaoFactory implements Factory<RecipeDao> {
  private final DaoModule module;

  private final Provider<DynamoDBMapper> mapperProvider;

  public DaoModule_ProvideRecipeDaoFactory(DaoModule module,
      Provider<DynamoDBMapper> mapperProvider) {
    this.module = module;
    this.mapperProvider = mapperProvider;
  }

  @Override
  public RecipeDao get() {
    return provideRecipeDao(module, mapperProvider.get());
  }

  public static DaoModule_ProvideRecipeDaoFactory create(DaoModule module,
      Provider<DynamoDBMapper> mapperProvider) {
    return new DaoModule_ProvideRecipeDaoFactory(module, mapperProvider);
  }

  public static RecipeDao provideRecipeDao(DaoModule instance, DynamoDBMapper mapper) {
    return Preconditions.checkNotNullFromProvides(instance.provideRecipeDao(mapper));
  }
}
