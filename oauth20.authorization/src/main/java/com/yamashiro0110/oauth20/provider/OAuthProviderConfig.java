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
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.Resource;

public class OAuthProviderConfig {

    @Order(100)
    @Configuration
    @EnableAuthorizationServer
    public static class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
        @Resource(name = "oAuthClientDetailsService")
        private ClientDetailsService clientDetailsService;
        @Resource(name = "clientAuthenticationManager")
        private AuthenticationManager authenticationManager;

        @Bean
        TokenStore tokenStore() {
            return new InMemoryTokenStore();
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints.tokenStore(this.tokenStore())
                    .authenticationManager(this.authenticationManager)
            ;
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) {
            security
//                    .checkTokenAccess("isAuthenticated()")
                    .tokenKeyAccess("permitAll()")
                    .passwordEncoder(NoOpPasswordEncoder.getInstance())
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

        @Bean
        public ClientDetailsUserDetailsService clientDetailsUserDetailsService() {
            ClientDetailsUserDetailsService clientDetailsUserDetailsService = new ClientDetailsUserDetailsService(this.clientDetailsService);
            clientDetailsUserDetailsService.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
            return clientDetailsUserDetailsService;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(this.clientDetailsUserDetailsService());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
//                    .antMatchers("/login").permitAll()
                    .antMatchers("/oauth/**").permitAll()
//                    .and()
//                    .formLogin()
//                    .and()
//                    .logout()
//                    .and()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .csrf().disable()
            ;
        }
    }

}
