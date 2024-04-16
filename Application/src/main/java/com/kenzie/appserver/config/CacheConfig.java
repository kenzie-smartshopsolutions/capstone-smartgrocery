package com.kenzie.appserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.annotation.EnableCaching;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableCaching
public class CacheConfig {

    // Create a Cache here if needed

   // @Bean
  //  public CacheStore myCache() {
      //  return new CacheStore(120, TimeUnit.SECONDS);
   // }


    /*When Spring initializes the application context,
    it looks for any CacheManager
     beans and uses them to manage caching operations

    Spring uses the ConcurrentMapCacheManager bean created
     by this method behind the scenes to manage caching
     for methods annotated with @Cacheable("Recipe")
     */
    @Bean
    public CacheManager cacheManager() {
       return new ConcurrentMapCacheManager("Recipe");
    }
}

/*@Bean method cacheManager() provides a way to configure the caching mechanism used by Spring
ConcurrentMapCacheManager bean with the name "Recipe"

Spring will use an in-memory cache provided by ConcurrentMapCacheManager to store the cached
results of methods annotated with @Cacheable("Recipe")
 */