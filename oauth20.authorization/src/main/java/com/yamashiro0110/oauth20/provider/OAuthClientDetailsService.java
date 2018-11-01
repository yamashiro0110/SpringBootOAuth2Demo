package com.yamashiro0110.oauth20.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service("customClientDetailsService")
public class OAuthClientDetailsService implements ClientDetailsService {
    @Value("${oauth2.demo.oauthClientEndpoint:http://localhost:8080/client}")
    private String oauthClientEndpoint;
    @Resource
    private ClientDetailsMapper clientDetailsMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetails clientDetails = this.clientDetailsMapper.findByClientId(clientId);
        Assert.notNull(clientDetails, "invalid client-id:" + clientId);
        return clientDetails;
    }
}
