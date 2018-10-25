package com.yamashiro0110.oauth20.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/simple/client")
public class SampleClientApiController {
    @Resource(name = "oAuth2ResourceRestTemplate")
    private OAuth2RestTemplate oAuth2RestTemplate;
    @Value("${oauth2.demo.sampleApiEndpoint:http://localhost:8090/resource/api/simple/resource}")
    private String resourceEndpoint;

    @GetMapping
    ResponseEntity<Object> getOAuthResource() {
        Object response = this.oAuth2RestTemplate.getForEntity(this.resourceEndpoint, Object.class);
        log.info("oauth resource response:{}", response);
        return ResponseEntity.ok(response);
    }

}
