package com.yamashiro0110.oauth20.provider;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.oauth2.provider.ClientDetails;

@Mapper
public interface ClientDetailsMapper {

    ClientDetails findByClientId(@Param("clientId") String clientId);
}
