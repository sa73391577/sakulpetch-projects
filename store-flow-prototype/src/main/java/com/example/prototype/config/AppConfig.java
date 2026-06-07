package com.example.prototype.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {
	/* 
     * Password encoder bean (uses BCrypt hashing)
     * Critical for secure password storage
     */
	
	
	private final static Logger logger = LoggerFactory.getLogger(AppConfig.class);
	
	
    @Bean
    public PasswordEncoder passwordEncoder() {
       
    	logger.info("logger passwordEncoder !!!");
    	//return NoOpPasswordEncoder.getInstance(); //==> password is painText.
    	//return new BCryptPasswordEncoder(); ==> password is BCryptPasswordEncoder.
    	return new MessageDigestPasswordEncoder("MD5");
    }

}
