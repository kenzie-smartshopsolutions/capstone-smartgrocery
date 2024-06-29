package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.UserRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@EnableScan
public interface UserRepository extends CrudRepository<UserRecord, String> {
    UserRecord findByUserId(String userId);
    UserRecord findByEmail(String email);
    UserRecord findByUsername(String username);
    @NonNull
    List<UserRecord> findAll();
}
