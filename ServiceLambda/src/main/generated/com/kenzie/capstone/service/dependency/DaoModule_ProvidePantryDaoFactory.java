package com.kenzie.capstone.service.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.capstone.service.dao.PantryDao;
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
public final class DaoModule_ProvidePantryDaoFactory implements Factory<PantryDao> {
  private final DaoModule module;

  private final Provider<DynamoDBMapper> mapperProvider;

  public DaoModule_ProvidePantryDaoFactory(DaoModule module,
      Provider<DynamoDBMapper> mapperProvider) {
    this.module = module;
    this.mapperProvider = mapperProvider;
  }

  @Override
  public PantryDao get() {
    return providePantryDao(module, mapperProvider.get());
  }

  public static DaoModule_ProvidePantryDaoFactory create(DaoModule module,
      Provider<DynamoDBMapper> mapperProvider) {
    return new DaoModule_ProvidePantryDaoFactory(module, mapperProvider);
  }

  public static PantryDao providePantryDao(DaoModule instance, DynamoDBMapper mapper) {
    return Preconditions.checkNotNullFromProvides(instance.providePantryDao(mapper));
  }
}
