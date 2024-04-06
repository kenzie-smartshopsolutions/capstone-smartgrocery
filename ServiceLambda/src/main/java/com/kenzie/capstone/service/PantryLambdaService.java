package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.ExampleDao;
import com.kenzie.capstone.service.dao.PantryDao;
import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.*;
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


    public PantryData getPantryData(String userId) {
        PantryRecord pantryRecord = pantryDao.getPantryRecord(userId);
        return pantryDao.convertToPantryData(pantryRecord);
    }

        public PantryData setPantryData(PantryData pantryData) {
        PantryRecord pantryRecord = pantryDao.convertToPantryRecord(pantryData);
        pantryRecord = pantryDao.storePantryRecord(pantryRecord);
        return pantryDao.convertToPantryData(pantryRecord);
    }



}
