package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.PantryDao;
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
public final class PantryLambdaService_Factory implements Factory<PantryLambdaService> {
  private final Provider<PantryDao> pantryDaoProvider;

  public PantryLambdaService_Factory(Provider<PantryDao> pantryDaoProvider) {
    this.pantryDaoProvider = pantryDaoProvider;
  }

  @Override
  public PantryLambdaService get() {
    return newInstance(pantryDaoProvider.get());
  }

  public static PantryLambdaService_Factory create(Provider<PantryDao> pantryDaoProvider) {
    return new PantryLambdaService_Factory(pantryDaoProvider);
  }

  public static PantryLambdaService newInstance(PantryDao pantryDao) {
    return new PantryLambdaService(pantryDao);
  }
}
