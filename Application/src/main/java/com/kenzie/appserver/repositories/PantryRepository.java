package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.PantryRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface PantryRepository extends CrudRepository<PantryRecord, String> {
    List<PantryRecord> findByUserId(String userId);

}
