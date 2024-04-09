package com.kenzie.appserver.repositories.model;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ExcludeFromJacocoGeneratedReport
@Service
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
    /**
     * This method should return a map of category id to category description.
     * The map should be populated from the food_category.csv file.
     * @Cacheable annotation tells Spring to cache the result of getAllCategoriesWithDescriptions()
     * for Frontend
     */
    @Cacheable("category")
    public Map<String, String> getAllCategoriesWithDescriptions() {
        // Method implementation that loads categories from CSV or other source
        return null;
    }
}