package com.asm.estore.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {
    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         return httpSecurity
                 .csrf(csrf -> csrf.disable())
                 .sessionManagement(session ->
                         session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 )
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/api/admin/**").hasRole("admin")
                         .requestMatchers("/api/user/**").hasRole("client")
                         .requestMatchers("/api/client/create").permitAll()
                         .anyRequest().permitAll()
                 )
                 .build();
     }
}
