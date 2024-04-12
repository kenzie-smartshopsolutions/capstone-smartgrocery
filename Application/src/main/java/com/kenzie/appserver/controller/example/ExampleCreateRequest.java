package com.kenzie.appserver.controller.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.ata.ExcludeFromJacocoGeneratedReport;

import javax.validation.constraints.NotEmpty;

@ExcludeFromJacocoGeneratedReport
public class ExampleCreateRequest {

    @NotEmpty
    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
