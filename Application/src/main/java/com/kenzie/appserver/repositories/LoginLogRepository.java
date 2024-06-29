package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.LoginLog;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableScan
@Repository
public interface LoginLogRepository extends CrudRepository<LoginLog, Long> {
    @NonNull
    List<LoginLog> findByUserId(@NonNull String userId);

    @NonNull
    List<LoginLog> findByUserIdAndTime(@NonNull String userId, @NonNull String date);

    @NonNull
    List<LoginLog> findByDate(@NonNull String date);


}
