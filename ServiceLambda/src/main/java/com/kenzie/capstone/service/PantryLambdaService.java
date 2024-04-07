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

//import static com.google.common.util.concurrent.SerializingExecutor.log;

public class PantryLambdaService {
    private PantryDao pantryDao;
    private static final Logger log = LoggerFactory.getLogger(UserLambdaService.class);

    @Inject
    public PantryLambdaService(PantryDao pantryDao) {
        this.pantryDao = pantryDao;
    }


    public PantryData getPantryData(String pantryItemId) {
        PantryRecord pantryRecord = pantryDao.getPantryRecord(pantryItemId);
        return pantryDao.convertToPantryData(pantryRecord);
    }

        public PantryData setPantryData(PantryData pantryData) {
        String pantryItemId = pantryData.getPantryItemId();
        PantryRecord pantryRecord = pantryDao.setPantryData(pantryItemId, pantryData);
        return pantryDao.convertToPantryData(pantryRecord);
    }
}
