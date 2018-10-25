package com.yamashiro0110.oauth20.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/simple/resource")
public class SimpleResourceApiController {

    @GetMapping
    ResponseEntity<Object> getResource() {
        Map<String, String> simpleResponse = new HashMap<>();
        simpleResponse.put("id", UUID.randomUUID().toString());
        return ResponseEntity.ok(simpleResponse);
    }
}
