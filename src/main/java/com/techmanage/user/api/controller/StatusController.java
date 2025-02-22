package com.techmanage.user.api.controller;

import com.techmanage.user.api.openapi.controller.StatusControllerOpenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequestMapping("/api/status")
@RestController
public class StatusController implements StatusControllerOpenApi {

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getApiStatus() {
        return new ResponseEntity<>(getSuccessResponse(), HttpStatus.OK);
    }

    private HashMap<String, Object> getSuccessResponse() {
        var response = new HashMap<String, Object>();
        response.put("service", "tech-manage-api");
        response.put("status", "success");
        response.put("statusCode", HttpStatus.OK.value());
        return response;
    }

}
