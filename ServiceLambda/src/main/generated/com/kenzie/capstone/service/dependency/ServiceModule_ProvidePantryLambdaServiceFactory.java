package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.PantryLambdaService;
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
public final class ServiceModule_ProvidePantryLambdaServiceFactory implements Factory<PantryLambdaService> {
  private final ServiceModule module;

  private final Provider<PantryDao> pantryDaoProvider;

  public ServiceModule_ProvidePantryLambdaServiceFactory(ServiceModule module,
      Provider<PantryDao> pantryDaoProvider) {
    this.module = module;
    this.pantryDaoProvider = pantryDaoProvider;
  }

  @Override
  public PantryLambdaService get() {
    return providePantryLambdaService(module, pantryDaoProvider.get());
  }

  public static ServiceModule_ProvidePantryLambdaServiceFactory create(ServiceModule module,
      Provider<PantryDao> pantryDaoProvider) {
    return new ServiceModule_ProvidePantryLambdaServiceFactory(module, pantryDaoProvider);
  }

  public static PantryLambdaService providePantryLambdaService(ServiceModule instance,
      PantryDao pantryDao) {
    return Preconditions.checkNotNullFromProvides(instance.providePantryLambdaService(pantryDao));
  }
}
