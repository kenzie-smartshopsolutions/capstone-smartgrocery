package com.kenzie.appserver.repositories.model;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class FoodCategoryConverter {
    public static Map<String, String> convertCsvToCategoryMap() throws IOException {
        File file = new File("com/kenzie/appserver/repositories/model/food_category.csv");
        CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();

        Map<String, String> foodCategories = new HashMap<>();
        try (MappingIterator<Map<?, ?>> mappingIterator = csvMapper.readerFor(Map.class).with(bootstrap).readValues(file)) {
            for (Map<?, ?> row : mappingIterator.readAll()) {

                // Uses the "id" column
                String id = (String) row.get("id");

                // Uses "description column
                String description = (String) row.get("description");

                foodCategories.put(id, description);
            }
        }
        return foodCategories;
    }


}