package com.greip.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.servlet.DispatcherServlet;

import javax.sql.DataSource;

@Configuration
@PropertySource({ "classpath:persistence.properties" })
//@PropertySource({ "file:/Users/apple/Documents/Projects/exchange/exchange-core.properties" })
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private Environment env;

    private TokenExtractor tokenExtractor = new BearerTokenExtractor();

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/api/socio-negocio/getsByDocumentoPublic/**").permitAll()
                .antMatchers("/api/parametro/getsNumDocumento").permitAll()
                .antMatchers("/api/usuario/registro-login").permitAll()
                .antMatchers("/api/usuario/recuperar-contrasena").permitAll()
                .antMatchers("/api/tipo-cambio/getTCExchange/**").permitAll()
                .anyRequest()
                .authenticated();
		// @formatter:on
    }

    // Remote token service
    /*
    @Primary
    @Bean
    public RemoteTokenServices tokenService() {
        final RemoteTokenServices tokenService = new RemoteTokenServices();
        tokenService.setCheckTokenEndpointUrl("http://localhost:8081/spring-security-oauth-server/oauth/check_token");
        tokenService.setClientId("fooClientIdPassword");
        tokenService.setClientSecret("secret");
        return tokenService;
    }
    */

    // JWT token store

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }

    /*
    @Bean
    public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
    }
    
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
    final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    // converter.setSigningKey("123");
    final Resource resource = new ClassPathResource("public.txt");
    String publicKey = null;
    try {
        publicKey = IOUtils.toString(resource.getInputStream());
    } catch (final IOException e) {
        throw new RuntimeException(e);
    }
    converter.setVerifierKey(publicKey);
    return converter;
    }
    */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);//Agregado
        return defaultTokenServices;
    }

    // JDBC token store configuration

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));
        return dataSource;
    }

    @Bean
    DispatcherServlet dispatcherServlet () {
        DispatcherServlet ds = new DispatcherServlet();
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource());
    }

}
