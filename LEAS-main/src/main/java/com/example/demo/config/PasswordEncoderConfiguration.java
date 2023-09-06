package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfiguration {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new PasswordEncoder(	) {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				// TODO Auto-generated method stub
				return BCrypt.checkpw(rawPassword.toString(),encodedPassword);
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				return BCrypt.hashpw(rawPassword.toString(),BCrypt.gensalt(10));
			}
		};
	}

}
