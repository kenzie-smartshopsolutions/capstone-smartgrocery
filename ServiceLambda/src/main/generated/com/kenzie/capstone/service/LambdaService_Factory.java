package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.ExampleDao;
import dagger.internal.Factory;
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
public final class LambdaService_Factory implements Factory<LambdaService> {
  private final Provider<ExampleDao> exampleDaoProvider;

  public LambdaService_Factory(Provider<ExampleDao> exampleDaoProvider) {
    this.exampleDaoProvider = exampleDaoProvider;
  }

  @Override
  public LambdaService get() {
    return newInstance(exampleDaoProvider.get());
  }

  public static LambdaService_Factory create(Provider<ExampleDao> exampleDaoProvider) {
    return new LambdaService_Factory(exampleDaoProvider);
  }

  public static LambdaService newInstance(ExampleDao exampleDao) {
    return new LambdaService(exampleDao);
  }
}
