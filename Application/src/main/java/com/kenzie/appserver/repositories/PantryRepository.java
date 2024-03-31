package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.PantryRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
@EnableScan
public interface PantryRepository extends CrudRepository<PantryRecord, String> {
    List<PantryRecord> findByUserId(String userId);
    //PantryRecord findById(String userId);

}
