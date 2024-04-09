package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.ExampleRecord;

import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@ExcludeFromJacocoGeneratedReport
@EnableScan
public interface ExampleRepository extends CrudRepository<ExampleRecord, String> {
}
