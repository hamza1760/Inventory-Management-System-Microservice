package com.inventorysystem.gateway.config.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    /**
     * Summary: Security Authorization.
     *
     * @param http http
     * @return SecurityWebFilterChain
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable().cors().disable();
        return http
            .exceptionHandling()
            .authenticationEntryPoint((swe, e) ->
                Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.BAD_REQUEST))
            ).accessDeniedHandler((swe, e) ->
                Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))
            ).and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .addFilterBefore(securityFilter, SecurityWebFiltersOrder.AUTHORIZATION)
//            .authenticationManager(authenticationManager)
//            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .pathMatchers(HttpMethod.OPTIONS).permitAll()
            .pathMatchers("/swagger-resources", "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security", "/swagger-ui/**").permitAll()
            .pathMatchers(HttpMethod.GET, "/service/**").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/user").permitAll()
            .pathMatchers(HttpMethod.GET, "/api/user").permitAll()
            .pathMatchers(HttpMethod.GET, "/api/realm/**").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/realm").permitAll()
            .pathMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
            .anyExchange()
            .authenticated()
            .and()
            .build();
    }
}
