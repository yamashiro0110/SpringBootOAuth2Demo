package com.yamashiro0110.oauth20.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Slf4j
@Service("oAuthClientDetailsService")
public class OAuthClientDetailsService implements ClientDetailsService {
    @Value("${oauth2.demo.oauthClientEndpoint:http://localhost:8080/client}")
    private String oauthClientEndpoint;
    @Resource
    private ClientDetailsMapper clientDetailsMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseClientDetails clientDetails = this.clientDetailsMapper.findByClientId(clientId);
        clientDetails.setScope(this.clientDetailsMapper.findScopesByClientId(clientId));
        clientDetails.setResourceIds(this.clientDetailsMapper.findResourceIdsByClientId(clientId));
        clientDetails.setRegisteredRedirectUri(this.clientDetailsMapper.findRedirectUrlsByClientId(clientId));
        clientDetails.setAuthorizedGrantTypes(this.clientDetailsMapper.findAuthorizedGrantTypesByClientId(clientId));

        Assert.notNull(clientDetails, "invalid client-id:" + clientId);
        log.debug("client:{} {}", clientId, clientDetails);
        return clientDetails;
    }
}
