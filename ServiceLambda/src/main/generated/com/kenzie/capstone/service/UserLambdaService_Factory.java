package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.UserDao;
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
public final class UserLambdaService_Factory implements Factory<UserLambdaService> {
  private final Provider<UserDao> userDaoProvider;

  public UserLambdaService_Factory(Provider<UserDao> userDaoProvider) {
    this.userDaoProvider = userDaoProvider;
  }

  @Override
  public UserLambdaService get() {
    return newInstance(userDaoProvider.get());
  }

  public static UserLambdaService_Factory create(Provider<UserDao> userDaoProvider) {
    return new UserLambdaService_Factory(userDaoProvider);
  }

  public static UserLambdaService newInstance(UserDao userDao) {
    return new UserLambdaService(userDao);
  }
}
