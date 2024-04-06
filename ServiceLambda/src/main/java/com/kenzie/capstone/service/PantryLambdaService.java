package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.PantryDao;
import com.kenzie.capstone.service.model.PantryData;
import com.kenzie.capstone.service.model.PantryRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

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
