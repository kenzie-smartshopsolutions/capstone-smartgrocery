package com.kenzie.capstone.service.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.capstone.service.dao.ExampleDao;
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
public final class DaoModule_ProvideExampleDaoFactory implements Factory<ExampleDao> {
  private final DaoModule module;

  private final Provider<DynamoDBMapper> mapperProvider;

  public DaoModule_ProvideExampleDaoFactory(DaoModule module,
      Provider<DynamoDBMapper> mapperProvider) {
    this.module = module;
    this.mapperProvider = mapperProvider;
  }

  @Override
  public ExampleDao get() {
    return provideExampleDao(module, mapperProvider.get());
  }

  public static DaoModule_ProvideExampleDaoFactory create(DaoModule module,
      Provider<DynamoDBMapper> mapperProvider) {
    return new DaoModule_ProvideExampleDaoFactory(module, mapperProvider);
  }

  public static ExampleDao provideExampleDao(DaoModule instance, DynamoDBMapper mapper) {
    return Preconditions.checkNotNullFromProvides(instance.provideExampleDao(mapper));
  }
}
