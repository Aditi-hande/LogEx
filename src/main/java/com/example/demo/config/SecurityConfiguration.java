package com.example.demo.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	 @Override
     protected void configure(HttpSecurity http) throws Exception {
         // @formatter:off	
         http
             .httpBasic().and()
             .authorizeRequests()
             .antMatchers("/login").permitAll()
             .anyRequest().authenticated()
             .and()
             .csrf().disable();
         http.cors();
     }
	
}
