package com.kenzie.capstone.service.dependency;

import com.kenzie.capstone.service.RecipeLambdaService;
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
public final class ServiceModule_ProvideRecipeLambdaServiceFactory implements Factory<RecipeLambdaService> {
  private final ServiceModule module;

  private final Provider<RecipeDao> recipeDaoProvider;

  public ServiceModule_ProvideRecipeLambdaServiceFactory(ServiceModule module,
      Provider<RecipeDao> recipeDaoProvider) {
    this.module = module;
    this.recipeDaoProvider = recipeDaoProvider;
  }

  @Override
  public RecipeLambdaService get() {
    return provideRecipeLambdaService(module, recipeDaoProvider.get());
  }

  public static ServiceModule_ProvideRecipeLambdaServiceFactory create(ServiceModule module,
      Provider<RecipeDao> recipeDaoProvider) {
    return new ServiceModule_ProvideRecipeLambdaServiceFactory(module, recipeDaoProvider);
  }

  public static RecipeLambdaService provideRecipeLambdaService(ServiceModule instance,
      RecipeDao recipeDao) {
    return Preconditions.checkNotNullFromProvides(instance.provideRecipeLambdaService(recipeDao));
  }
}
