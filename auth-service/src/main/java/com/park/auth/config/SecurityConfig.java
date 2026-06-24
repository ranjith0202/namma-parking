package com.park.auth.config;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfiguration;

import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
            );

        return http.build();
    }
	
	  @Bean
	   AuthenticationManager authenticationManager(
	            AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }
	  
	  @Bean
	  PasswordEncoder passwordEncoder() {
	      return NoOpPasswordEncoder.getInstance();
	  }
	  
	  @Bean
	  CorsConfigurationSource corsConfigurationSource() {

	      CorsConfiguration configuration = new CorsConfiguration();

	      configuration.setAllowedOrigins(
	              List.of("http://localhost:4200"));

	      configuration.setAllowedMethods(
	              List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

	      configuration.setAllowedHeaders(
	              List.of("*"));

	      configuration.setAllowCredentials(true);

	      UrlBasedCorsConfigurationSource source =
	              new UrlBasedCorsConfigurationSource();

	      source.registerCorsConfiguration("/**", configuration);

	      return source;
	  }
}