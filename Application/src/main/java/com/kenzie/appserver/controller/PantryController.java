package com.kenzie.appserver.controller;

import com.kenzie.appserver.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public class PantryController {

    private PantryService pantryService;

    PantryController(PantryService pantryService) {
        this.pantryService = pantryService;

    @PostMapping("/addItem")
    public ResponseEntity<String> addPantryItem(@RequestBody PantryItem pantryItem) {
        // Implement logic to add a pantry item
        pantryService.addPantryItem(pantryItem);
        return ResponseEntity.ok("Pantry item added successfully");
    }

    // Other pantry-related endpoints can be added here
}
}
