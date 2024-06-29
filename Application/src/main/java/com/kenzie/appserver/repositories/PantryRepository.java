package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.PantryRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableScan
public interface PantryRepository extends CrudRepository<PantryRecord, String> {
    //when you want to retrieve all pantry items belonging to a particular user
    List<PantryRecord> findByUserId(String userId);


    //when you need to fetch a specific pantry item by its ID
    PantryRecord findItemByPantryItemId(String pantryItemId);
}
