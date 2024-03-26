package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.PantryRecord;
import com.kenzie.appserver.repositories.model.RecipeRecord;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<RecipeRecord, String> {
}
