package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.PantryDao;
import com.kenzie.capstone.service.model.PantryData;
import com.kenzie.capstone.service.model.PantryRecord;
import com.kenzie.capstone.service.model.UserData;
import com.kenzie.capstone.service.model.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//import static com.google.common.util.concurrent.SerializingExecutor.log;

public class PantryLambdaService {
    private PantryDao pantryDao;
    private static final Logger log = LoggerFactory.getLogger(UserLambdaService.class);

    @Inject
    public PantryLambdaService(PantryDao pantryDao) {
        this.pantryDao = pantryDao;
    }


    public PantryData getPantryData(String pantryItemId) {
        try {
            PantryRecord pantryRecord = pantryDao.getPantryRecord(pantryItemId);
            // Handle item not found scenario
            if (pantryRecord == null) {
                log.error("Pantry item not found with ID: {}", pantryItemId);
                throw new RuntimeException("Pantry item not found");
            }
            return pantryDao.convertToPantryData(pantryRecord);
        } catch (Exception e) {
            log.error("Error fetching pantry data for ID: {}, error: {}", pantryItemId, e.getMessage());
            throw e;
        }
    }

        public PantryData setPantryData(PantryData pantryData) {
        String pantryItemId = pantryData.getPantryItemId();
        PantryRecord pantryRecord = pantryDao.setPantryData(pantryItemId, pantryData);
        return pantryDao.convertToPantryData(pantryRecord);

    }
    public List<PantryData> getPantryDataByUserId(String userId) {
        List<PantryRecord> pantryRecords = pantryDao.getPantryRecordsByUserId(userId);
        return pantryRecords.stream()
                .map(record -> pantryDao.convertToPantryData(record))
                .collect(Collectors.toList());
    }
}
