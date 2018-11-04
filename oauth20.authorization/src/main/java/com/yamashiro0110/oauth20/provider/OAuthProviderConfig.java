package com.yamashiro0110.oauth20.provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class OAuthProviderConfig {

    @Order(100)
    @Configuration
    @EnableAuthorizationServer
    public static class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
        @Resource(name = "oAuthClientDetailsService")
        private ClientDetailsService clientDetailsService;
        @Resource(name = "clientDetailsUserDetailsService")
        private ClientDetailsUserDetailsService clientDetailsUserDetailsService;
        @Resource(name = "clientAuthenticationManager")
        private AuthenticationManager authenticationManager;
        @Resource
        private DataSource dataSource;

        @Bean
        TokenStore tokenStore() {
            return new JdbcTokenStore(this.dataSource);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints
                    .tokenStore(this.tokenStore())
                    .userDetailsService(this.clientDetailsUserDetailsService)
                    /*
                     * authenticationManagerを設定することでResourceOwnerPasswordTokenGranterが有効になり、
                     * resource owner password credential grantが有効になる
                     */
                    .authenticationManager(this.authenticationManager)
            ;
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) {
            security.passwordEncoder(NoOpPasswordEncoder.getInstance())
                    .checkTokenAccess("isAuthenticated()")
                    .tokenKeyAccess("permitAll()")
            ;
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(this.clientDetailsService);
        }
    }

    @Order
    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Resource(name = "oAuthClientDetailsService")
        private ClientDetailsService clientDetailsService;

        @Bean("clientAuthenticationManager")
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean("clientDetailsUserDetailsService")
        public ClientDetailsUserDetailsService clientDetailsUserDetailsService() {
            ClientDetailsUserDetailsService clientDetailsUserDetailsService = new ClientDetailsUserDetailsService(this.clientDetailsService);
            clientDetailsUserDetailsService.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
            return clientDetailsUserDetailsService;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(this.clientDetailsUserDetailsService())
                    .passwordEncoder(NoOpPasswordEncoder.getInstance())
            ;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                    .and()
                    .csrf().disable()
            ;
        }
    }

}
