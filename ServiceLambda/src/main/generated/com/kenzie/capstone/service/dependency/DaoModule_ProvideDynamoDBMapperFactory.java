package com.kenzie.capstone.service.dependency;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaoModule_ProvideDynamoDBMapperFactory implements Factory<DynamoDBMapper> {
  private final DaoModule module;

  public DaoModule_ProvideDynamoDBMapperFactory(DaoModule module) {
    this.module = module;
  }

  @Override
  public DynamoDBMapper get() {
    return provideDynamoDBMapper(module);
  }

  public static DaoModule_ProvideDynamoDBMapperFactory create(DaoModule module) {
    return new DaoModule_ProvideDynamoDBMapperFactory(module);
  }

  public static DynamoDBMapper provideDynamoDBMapper(DaoModule instance) {
    return Preconditions.checkNotNullFromProvides(instance.provideDynamoDBMapper());
  }
}
