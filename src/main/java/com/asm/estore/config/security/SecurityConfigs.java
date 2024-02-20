package com.asm.estore.config.security;


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
                         .requestMatchers("/api/category/admin/**").hasRole("admin")
                         .requestMatchers("/api/category/user/**").hasRole("user")
                         .requestMatchers("/api/client/admin/**").hasRole("admin")
                         .requestMatchers("/api/client/user/**").hasRole("user")
                         .requestMatchers("/api/order_product/user/**").hasRole("user")
                         .requestMatchers("/api/order_product/admin/**").hasRole("admin")
                         .requestMatchers("/api/order/user/**").hasRole("user")
                         .requestMatchers("/api/order/admin/**").hasRole("admin")
                         .requestMatchers("/api/product/user/**").hasRole("user")
                         .requestMatchers("/api/product/admin/**").hasRole("admin")
                         
                         .requestMatchers("/api/client/create").permitAll()
                         .requestMatchers("/api/auth/**").permitAll()
                 )
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
