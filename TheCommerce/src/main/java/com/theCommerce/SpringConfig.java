package com.theCommerce;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.theCommerce.repository.UserRepository;
import com.theCommerce.service.UserService;

@Configuration
public class SpringConfig {

	private final UserRepository userRepository;
	
	@Autowired
	public SpringConfig(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Bean
	public UserService userService() {
		return new UserService(userRepository);
	}
	
}
