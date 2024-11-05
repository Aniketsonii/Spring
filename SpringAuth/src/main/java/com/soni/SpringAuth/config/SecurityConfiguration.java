package com.soni.SpringAuth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.soni.SpringAuth.common.CONSTANT;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

       http
        .csrf(customizer -> customizer.disable())
        .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(customizer -> {
          customizer
              .requestMatchers("/api", "/api/", "/api/auth/signin", "/api/auth/signin/", "/api/auth/signup",
                  "/api/auth/signup/")
              .permitAll()

              .requestMatchers("/api/admin/**")
              .hasAuthority(CONSTANT.ROLE_ADMINISTRATOR)

              .requestMatchers(HttpMethod.GET, "/api/users/**")
              .hasAuthority(CONSTANT.ROLE_ADMINISTRATOR)

              .requestMatchers(HttpMethod.PUT, "/api/users", "/api/users/**", "/api/auth/logout", "/api/auth/logout/")
              .hasAnyAuthority(CONSTANT.ROLE_USER, CONSTANT.ROLE_ADMINISTRATOR)

              .requestMatchers(HttpMethod.DELETE, "/api/users", "/api/users/**")
              .hasAnyAuthority(CONSTANT.ROLE_ADMINISTRATOR)


              .anyRequest()
              .authenticated();
        });
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

}
