package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.LambdaService;
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
public final class ServiceModule_ProvideLambdaServiceFactory implements Factory<LambdaService> {
  private final ServiceModule module;

  private final Provider<ExampleDao> exampleDaoProvider;

  public ServiceModule_ProvideLambdaServiceFactory(ServiceModule module,
      Provider<ExampleDao> exampleDaoProvider) {
    this.module = module;
    this.exampleDaoProvider = exampleDaoProvider;
  }

  @Override
  public LambdaService get() {
    return provideLambdaService(module, exampleDaoProvider.get());
  }

  public static ServiceModule_ProvideLambdaServiceFactory create(ServiceModule module,
      Provider<ExampleDao> exampleDaoProvider) {
    return new ServiceModule_ProvideLambdaServiceFactory(module, exampleDaoProvider);
  }

  public static LambdaService provideLambdaService(ServiceModule instance, ExampleDao exampleDao) {
    return Preconditions.checkNotNullFromProvides(instance.provideLambdaService(exampleDao));
  }
}
