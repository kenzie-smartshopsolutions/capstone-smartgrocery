package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.LoginLog;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@EnableScan
@Repository
public interface LoginLogRepository extends CrudRepository<LoginLog, String> {
    @NonNull
    List<LoginLog> findByUserId(@NonNull String userId);

    @NonNull
    List<LoginLog> findByUserIdAndDate(@NonNull String userId, @NonNull String date);

    @NonNull
    List<LoginLog> findByDate(@NonNull String date);

    @NonNull
    List<LoginLog> findByUsername(@NonNull String username);

    @NonNull
    List<LoginLog> findAll();

}
