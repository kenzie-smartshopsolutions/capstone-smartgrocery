package com.kenzie.capstone.service.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.dao.UserDao;
import dagger.MembersInjector;
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
public final class DaoModule_MembersInjector implements MembersInjector<DaoModule> {
  private final Provider<DynamoDBMapper> mapperProvider;

  private final Provider<DynamoDBMapper> mapperProvider2;

  public DaoModule_MembersInjector(Provider<DynamoDBMapper> mapperProvider,
      Provider<DynamoDBMapper> mapperProvider2) {
    this.mapperProvider = mapperProvider;
    this.mapperProvider2 = mapperProvider2;
  }

  public static MembersInjector<DaoModule> create(Provider<DynamoDBMapper> mapperProvider,
      Provider<DynamoDBMapper> mapperProvider2) {
    return new DaoModule_MembersInjector(mapperProvider, mapperProvider2);
  }

  @Override
  public void injectMembers(DaoModule instance) {
    injectProvideExampleDao(instance, mapperProvider.get());
    injectProvideUserDao(instance, mapperProvider2.get());
  }

  public static ExampleDao injectProvideExampleDao(DaoModule instance, DynamoDBMapper mapper) {
    return instance.provideExampleDao(mapper);
  }

  public static UserDao injectProvideUserDao(DaoModule instance, DynamoDBMapper mapper) {
    return instance.provideUserDao(mapper);
  }
}
