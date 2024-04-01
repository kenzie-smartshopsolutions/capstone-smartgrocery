package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.LambdaService;
import com.kenzie.capstone.service.UserLambdaService;
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
public final class ServiceModule_MembersInjector implements MembersInjector<ServiceModule> {
  private final Provider<ExampleDao> exampleDaoProvider;

  private final Provider<UserDao> userDaoProvider;

  public ServiceModule_MembersInjector(Provider<ExampleDao> exampleDaoProvider,
      Provider<UserDao> userDaoProvider) {
    this.exampleDaoProvider = exampleDaoProvider;
    this.userDaoProvider = userDaoProvider;
  }

  public static MembersInjector<ServiceModule> create(Provider<ExampleDao> exampleDaoProvider,
      Provider<UserDao> userDaoProvider) {
    return new ServiceModule_MembersInjector(exampleDaoProvider, userDaoProvider);
  }

  @Override
  public void injectMembers(ServiceModule instance) {
    injectProvideLambdaService(instance, exampleDaoProvider.get());
    injectProvideUserLambdaService(instance, userDaoProvider.get());
  }

  public static LambdaService injectProvideLambdaService(ServiceModule instance,
      ExampleDao exampleDao) {
    return instance.provideLambdaService(exampleDao);
  }

  public static UserLambdaService injectProvideUserLambdaService(ServiceModule instance,
      UserDao userDao) {
    return instance.provideUserLambdaService(userDao);
  }
}
