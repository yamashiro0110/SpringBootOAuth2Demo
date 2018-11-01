package com.yamashiro0110.oauth20.provider;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.List;
import java.util.Set;

@Mapper
public interface ClientDetailsMapper {

    BaseClientDetails findByClientId(@Param("clientId") String clientId);

    List<String> findScopesByClientId(@Param("clientId") String clientId);

    List<String> findResourceIdsByClientId(@Param("clientId") String clientId);

    List<String> findAuthorizedGrantTypesByClientId(@Param("clientId") String clientId);

    Set<String> findRedirectUrlsByClientId(@Param("clientId") String clientId);
}
