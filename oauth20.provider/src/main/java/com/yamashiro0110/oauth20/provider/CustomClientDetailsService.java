package com.yamashiro0110.oauth20.provider;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class CustomClientDetailsService implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientId);
        clientDetails.setClientSecret("sample-client-secret");
        clientDetails.setScope(Arrays.asList("read", "write"));
        clientDetails.setResourceIds(Arrays.asList("resource_1", "resource_2"));
        clientDetails.setAuthorizedGrantTypes(Arrays.asList("authorization_code"));
        clientDetails.setRegisteredRedirectUri(this.redirectUrls());
        clientDetails.setAuthorizedGrantTypes(Arrays.asList("user"));
        clientDetails.setAccessTokenValiditySeconds(3600);
        clientDetails.setRefreshTokenValiditySeconds(3600 * 24 * 90);
        return clientDetails;
    }

    private Set<String> redirectUrls() {
        Set<String> redirectUrl = new LinkedHashSet<>();
        redirectUrl.add("http://localhost:8100");
        return redirectUrl;
    }

}
