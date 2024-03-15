package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.IngredientRecord;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<IngredientRecord,String> {
}
