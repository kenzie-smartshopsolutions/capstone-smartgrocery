package com.kenzie.capstone.service.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.capstone.service.LambdaService;
import com.kenzie.capstone.service.PantryLambdaService;
import com.kenzie.capstone.service.RecipeLambdaService;
import com.kenzie.capstone.service.UserLambdaService;
import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.dao.PantryDao;
import com.kenzie.capstone.service.dao.RecipeDao;
import com.kenzie.capstone.service.dao.UserDao;
import dagger.internal.DoubleCheck;
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
public final class DaggerServiceComponent implements ServiceComponent {
  private Provider<DynamoDBMapper> provideDynamoDBMapperProvider;

  private Provider<ExampleDao> provideExampleDaoProvider;

  private Provider<LambdaService> provideLambdaServiceProvider;

  private Provider<UserDao> provideUserDaoProvider;

  private Provider<UserLambdaService> provideUserLambdaServiceProvider;

  private Provider<PantryDao> providePantryDaoProvider;

  private Provider<PantryLambdaService> providePantryLambdaServiceProvider;

  private Provider<RecipeDao> provideRecipeDaoProvider;

  private Provider<RecipeLambdaService> provideRecipeLambdaServiceProvider;

  private DaggerServiceComponent(DaoModule daoModuleParam, ServiceModule serviceModuleParam) {

    initialize(daoModuleParam, serviceModuleParam);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static ServiceComponent create() {
    return new Builder().build();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final DaoModule daoModuleParam, final ServiceModule serviceModuleParam) {
    this.provideDynamoDBMapperProvider = DoubleCheck.provider(DaoModule_ProvideDynamoDBMapperFactory.create(daoModuleParam));
    this.provideExampleDaoProvider = DoubleCheck.provider(DaoModule_ProvideExampleDaoFactory.create(daoModuleParam, provideDynamoDBMapperProvider));
    this.provideLambdaServiceProvider = DoubleCheck.provider(ServiceModule_ProvideLambdaServiceFactory.create(serviceModuleParam, provideExampleDaoProvider));
    this.provideUserDaoProvider = DoubleCheck.provider(DaoModule_ProvideUserDaoFactory.create(daoModuleParam, provideDynamoDBMapperProvider));
    this.provideUserLambdaServiceProvider = DoubleCheck.provider(ServiceModule_ProvideUserLambdaServiceFactory.create(serviceModuleParam, provideUserDaoProvider));
    this.providePantryDaoProvider = DoubleCheck.provider(DaoModule_ProvidePantryDaoFactory.create(daoModuleParam, provideDynamoDBMapperProvider));
    this.providePantryLambdaServiceProvider = DoubleCheck.provider(ServiceModule_ProvidePantryLambdaServiceFactory.create(serviceModuleParam, providePantryDaoProvider));
    this.provideRecipeDaoProvider = DoubleCheck.provider(DaoModule_ProvideRecipeDaoFactory.create(daoModuleParam, provideDynamoDBMapperProvider));
    this.provideRecipeLambdaServiceProvider = DoubleCheck.provider(ServiceModule_ProvideRecipeLambdaServiceFactory.create(serviceModuleParam, provideRecipeDaoProvider));
  }

  @Override
  public LambdaService provideLambdaService() {
    return provideLambdaServiceProvider.get();
  }

  @Override
  public UserLambdaService provideUserLambdaService() {
    return provideUserLambdaServiceProvider.get();
  }

  @Override
  public PantryLambdaService providePantryLambdaService() {
    return providePantryLambdaServiceProvider.get();
  }

  @Override
  public RecipeLambdaService provideRecipeLambdaService() {
    return provideRecipeLambdaServiceProvider.get();
  }

  public static final class Builder {
    private DaoModule daoModule;

    private ServiceModule serviceModule;

    private Builder() {
    }

    public Builder daoModule(DaoModule daoModule) {
      this.daoModule = Preconditions.checkNotNull(daoModule);
      return this;
    }

    public Builder serviceModule(ServiceModule serviceModule) {
      this.serviceModule = Preconditions.checkNotNull(serviceModule);
      return this;
    }

    public ServiceComponent build() {
      if (daoModule == null) {
        this.daoModule = new DaoModule();
      }
      if (serviceModule == null) {
        this.serviceModule = new ServiceModule();
      }
      return new DaggerServiceComponent(daoModule, serviceModule);
    }
  }
}
