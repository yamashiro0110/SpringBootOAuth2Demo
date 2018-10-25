package com.yamashiro0110.oauth20.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service("customClientDetailsService")
public class CustomClientDetailsService implements ClientDetailsService {
    @Value("${oauth2.demo.oauthClientEndpoint:http://localhost:8080/client}")
    private String oauthClientEndpoint;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret("sample-client-secret");
        clientDetails.setScope(this.scopes());
        clientDetails.setResourceIds(this.resourceIds());
        clientDetails.setAuthorizedGrantTypes(this.authorizedGrantTypes());
        clientDetails.setRegisteredRedirectUri(this.redirectUrls());
        clientDetails.setAccessTokenValiditySeconds(3600);
        clientDetails.setRefreshTokenValiditySeconds(3600 * 24 * 90);
        return clientDetails;
    }

    private List<String> resourceIds() {
        return Arrays.asList("oauth20-demo");
    }

    private Set<String> redirectUrls() {
        Set<String> redirectUrl = new LinkedHashSet<>();
        redirectUrl.add(this.oauthClientEndpoint + "/oauth");
        redirectUrl.add(this.oauthClientEndpoint + "/api/simple/client");
        return redirectUrl;
    }

    private List<String> authorizedGrantTypes() {
        return Arrays.asList("authorization_code");
    }

    private List<String> scopes() {
        return Arrays.asList("read", "write");
    }

}
