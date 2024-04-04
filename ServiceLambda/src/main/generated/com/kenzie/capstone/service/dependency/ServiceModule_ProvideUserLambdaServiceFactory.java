package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.UserLambdaService;
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
public final class ServiceModule_ProvideUserLambdaServiceFactory implements Factory<UserLambdaService> {
  private final ServiceModule module;

  private final Provider<UserDao> userDaoProvider;

  public ServiceModule_ProvideUserLambdaServiceFactory(ServiceModule module,
      Provider<UserDao> userDaoProvider) {
    this.module = module;
    this.userDaoProvider = userDaoProvider;
  }

  @Override
  public UserLambdaService get() {
    return provideUserLambdaService(module, userDaoProvider.get());
  }

  public static ServiceModule_ProvideUserLambdaServiceFactory create(ServiceModule module,
      Provider<UserDao> userDaoProvider) {
    return new ServiceModule_ProvideUserLambdaServiceFactory(module, userDaoProvider);
  }

  public static UserLambdaService provideUserLambdaService(ServiceModule instance,
      UserDao userDao) {
    return Preconditions.checkNotNullFromProvides(instance.provideUserLambdaService(userDao));
  }
}
