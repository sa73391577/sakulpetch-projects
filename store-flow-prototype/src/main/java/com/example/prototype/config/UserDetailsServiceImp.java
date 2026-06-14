package com.example.prototype.config;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.prototype.entity.master.Users;
import com.example.prototype.repository.jpa.UsersRepository;
import com.example.prototype.services.UserDetailImp;

@Primary
@Service
public class UserDetailsServiceImp implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImp.class);
	
	@Autowired
	UsersRepository usersRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		logger.info("loadUserByUsername working !!!");
		logger.info("username : {}",username);
		Users u = usersRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found Username : "+username));
		if(logger.isDebugEnabled()) {
			logger.debug("user log in info : {}",ToStringBuilder.reflectionToString(u));
		}
		return UserDetailImp.build(u);
	}

}
