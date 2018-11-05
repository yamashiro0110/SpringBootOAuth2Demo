package com.yamashiro0110.oauth20.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableOAuth2Client
@Data
public class ClientConfig extends WebSecurityConfigurerAdapter {
    @Value("${oauth2.demo.authorizationServerEndpoint:http://localhost:8100/provider}")
    private String authorizationServerEndpoint;
    @Value("${oauth2.client.demo.resourceId:resource_1}")
    private String resourceId;
    @Value("${oauth2.client.demo.clientId:client_id}")
    private String clientId;
    @Value("${oauth2.client.demo.clientSecret:client_secret}")
    private String clientSecret;
    @Value("${oauth2.client.demo.scope:read,write}")
    private String[] scope;

    private List<String> scopes() {
        return Arrays.asList(this.scope);
    }

    @Bean("oAuth2ResourceRestTemplate")
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext oAuth2ClientContext) {
        return new OAuth2RestTemplate(this.clientCredentialResourceDetails(), oAuth2ClientContext);
    }

    private OAuth2ProtectedResourceDetails authorizationCodeResourceDetails() {
        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
        resourceDetails.setId(this.resourceId);
        resourceDetails.setClientId(this.clientId);
        resourceDetails.setClientSecret(this.clientSecret);
        resourceDetails.setScope(this.scopes());
        resourceDetails.setAccessTokenUri(this.authorizationServerEndpoint + "/oauth/token");
        resourceDetails.setUserAuthorizationUri(this.authorizationServerEndpoint + "/oauth/authorize");
        return resourceDetails;
    }

    private OAuth2ProtectedResourceDetails clientCredentialResourceDetails() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setId(this.resourceId);
        resourceDetails.setClientId(this.clientId);
        resourceDetails.setClientSecret(this.clientSecret);
        resourceDetails.setScope(this.scopes());
        resourceDetails.setAccessTokenUri(this.authorizationServerEndpoint + "/oauth/token");
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
