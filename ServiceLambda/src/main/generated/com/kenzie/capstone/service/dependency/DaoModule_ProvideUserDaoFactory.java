package com.kenzie.capstone.service.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.capstone.service.dao.UserDao;
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
public final class DaoModule_ProvideUserDaoFactory implements Factory<UserDao> {
  private final DaoModule module;

  private final Provider<DynamoDBMapper> mapperProvider;

  public DaoModule_ProvideUserDaoFactory(DaoModule module,
      Provider<DynamoDBMapper> mapperProvider) {
    this.module = module;
    this.mapperProvider = mapperProvider;
  }

  @Override
  public UserDao get() {
    return provideUserDao(module, mapperProvider.get());
  }

  public static DaoModule_ProvideUserDaoFactory create(DaoModule module,
      Provider<DynamoDBMapper> mapperProvider) {
    return new DaoModule_ProvideUserDaoFactory(module, mapperProvider);
  }

  public static UserDao provideUserDao(DaoModule instance, DynamoDBMapper mapper) {
    return Preconditions.checkNotNullFromProvides(instance.provideUserDao(mapper));
  }
}
