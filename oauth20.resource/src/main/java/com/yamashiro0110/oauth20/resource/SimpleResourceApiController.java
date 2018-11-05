package com.yamashiro0110.oauth20.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/simple/resource")
public class SimpleResourceApiController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    Authentication authentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(authentication)) {
            return null;
        }

        logger.info("oauth authentication:{}", authentication);
        return authentication;
    }

    @GetMapping
    ResponseEntity<Object> getResource() {
        Map<String, String> simpleResponse = new HashMap<>();
        simpleResponse.put("id", UUID.randomUUID().toString());
        simpleResponse.put("authentication", Objects.toString(this.authentication()));
        return ResponseEntity.ok(simpleResponse);
    }
}
