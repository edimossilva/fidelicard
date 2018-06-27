package com.loop.fidelicard.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.service.LoginUserService;

@Configuration
@EnableAuthorizationServer
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class ConfigSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private LoginUserService loginUserService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		// return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		for (LoginUser loginUser : loginUserService.findAll()) {
			auth.inMemoryAuthentication().withUser(loginUser.getEmail()).password(loginUser.getPassword())
					.roles(loginUser.getUserRole() + "");
		}
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/guest/**").authorizeRequests().anyRequest().permitAll();
	}
}