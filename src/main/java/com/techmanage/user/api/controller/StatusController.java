package com.techmanage.user.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequestMapping("/api/status")
@RestController
public class StatusController {

    @GetMapping
    public ResponseEntity<HashMap<String, String>> getApiStatus() {
        return ResponseEntity.ok(getSuccessResponse());
    }

    private HashMap<String, String> getSuccessResponse() {
        var response = new HashMap<String, String>();
        response.put("service", "tech-manage-api");
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.name());
        return response;
    }

}
