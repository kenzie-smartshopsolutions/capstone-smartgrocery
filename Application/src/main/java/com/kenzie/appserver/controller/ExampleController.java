package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.example.ExampleCreateRequest;
import com.kenzie.appserver.controller.example.ExampleResponse;
import com.kenzie.appserver.service.ExampleService;
import com.kenzie.appserver.service.model.Example;
import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ExcludeFromJacocoGeneratedReport
@RestController
@RequestMapping("example")
public class ExampleController {

    private ExampleService exampleService;

    ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExampleResponse> get(@PathVariable("id") String id) {

        Example example = exampleService.findById(id);
        if (example == null) {
            return ResponseEntity.notFound().build();
        }

        ExampleResponse exampleResponse = new ExampleResponse();
        exampleResponse.setId(example.getId());
        exampleResponse.setName(example.getName());
        return ResponseEntity.ok(exampleResponse);
    }

    @PostMapping
    public ResponseEntity<ExampleResponse> addNewExample(@RequestBody ExampleCreateRequest exampleCreateRequest) {
        Example example = exampleService.addNewExample(exampleCreateRequest.getName());

        ExampleResponse exampleResponse = new ExampleResponse();
        exampleResponse.setId(example.getId());
        exampleResponse.setName(example.getName());

        return ResponseEntity.ok(exampleResponse);
    }
}
