package com.yamashiro0110.oauth20.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class ClientConfig extends WebSecurityConfigurerAdapter {
    @Value("${oauth2.demo.authorizationServerEndpoint:http://localhost:8100/provider}")
    private String authorizationServerEndpoint;

    @Bean("oAuth2ResourceRestTemplate")
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext oAuth2ClientContext) {
        return new OAuth2RestTemplate(this.oAuth2ProtectedResourceDetails(), oAuth2ClientContext);
    }

    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails() {
        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
        resourceDetails.setId("oauth20-demo");
        resourceDetails.setClientId("sample-client-id");
        resourceDetails.setClientSecret("sample-client-secret");
        resourceDetails.setScope(Arrays.asList("read", "write"));
        resourceDetails.setAccessTokenUri(this.authorizationServerEndpoint + "/oauth/token");
        resourceDetails.setUserAuthorizationUri(this.authorizationServerEndpoint + "/oauth/authorize");
        return resourceDetails;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.mvcMatcher("/*")
                .authorizeRequests()
                .anyRequest()
                .permitAll();
    }
}
