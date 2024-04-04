package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.UserRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface UserRepository extends CrudRepository<UserRecord, String> {
    UserRecord findByUserId(String userId);
    UserRecord findByEmail(String email);
    UserRecord findByUsername(String username);
}