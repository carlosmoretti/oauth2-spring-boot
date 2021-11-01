package com.carlosmoretti.oauth.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.carlosmoretti.oauth.example.service.impl.AuthenticationDetailServiceImpl;

@SuppressWarnings("deprecation")
public class OAuthServerConfig {
	private static final String RESOURCE_ID = "restservice";
	
	@Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends
            ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources
                    .resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
        	http
            .httpBasic()
                .and()
            .authorizeRequests()
                .antMatchers("/publico/**").permitAll()
                .and()
            .authorizeRequests()
                .antMatchers("/restrito/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .permitAll();
        }

    }

	@Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends
            AuthorizationServerConfigurerAdapter {

        private TokenStore tokenStore = new InMemoryTokenStore();
        
        @Autowired
        private Environment env;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Autowired
        private AuthenticationDetailServiceImpl userDetailsService;

        @Autowired
        private PasswordEncoder passwordEncoder;

		@Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                    .tokenStore(this.tokenStore)
                    .authenticationManager(this.authenticationManager)
                    .userDetailsService(userDetailsService);
        }

		@Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient(env.getProperty("basic.client"))
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("all")
                    .refreshTokenValiditySeconds(1200)
                    .resourceIds(RESOURCE_ID)
                    .secret(passwordEncoder.encode(env.getProperty("basic.password")))
                    .accessTokenValiditySeconds(1200);
        }

		@Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(this.tokenStore);
            return tokenServices;
        }

    }
}
