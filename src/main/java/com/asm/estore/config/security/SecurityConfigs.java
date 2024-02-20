package com.asm.estore.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         return httpSecurity
                 .csrf(csrf -> csrf.disable())
                 .sessionManagement(session ->
                         session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 )
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/admin/**").hasRole("admin")
                         .requestMatchers("/user/**").hasRole("user")
                         
                         .requestMatchers("/api/client/create").permitAll()
                         .requestMatchers("/api/auth/**").permitAll()
                         .anyRequest().authenticated()
                 )
                 .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                 .build();
     }

     @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
     }

     @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
