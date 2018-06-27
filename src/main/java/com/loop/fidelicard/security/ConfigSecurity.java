package com.loop.fidelicard.security;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.service.LoginUserService;

@Configuration
@EnableAuthorizationServer
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Resource
public class ConfigSecurity extends WebSecurityConfigurerAdapter {
	@Autowired
	private LoginUserService loginUserService;
	// @Autowired
	// private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		// return NoOpPasswordEncoder.getInstance();
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
				"/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/card");

	}
	// @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	// @Override
	// public AuthenticationManager authenticationManagerBean() throws Exception {
	// return super.authenticationManagerBean();
	// }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(inMemoryUserDetailsManager());
	}

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		final Properties users = new Properties();
		for (LoginUser loginUser : loginUserService.findAll()) {
			String credentials = loginUser.getPassword() + ",ROLE_" + loginUser.getUserRole() + ",enabled";

			users.put(loginUser.getEmail(), credentials);
			System.out.println("credentials =" + credentials);
		}
		// users.put("user", "pass,ROLE_USER,enabled"); // add whatever other user you
		// need
		return new InMemoryUserDetailsManager(users);
	}
	// @Override
	// protected void configure(AuthenticationManagerBuilder auth) throws Exception
	// {
	// // auth.userDetailsService(userDetailsService);
	// for (LoginUser loginUser : loginUserService.findAll()) {
	// auth.inMemoryAuthentication().withUser(loginUser.getEmail()).password(loginUser.getPassword())
	// .roles(loginUser.getUserRole() + "");
	// }
	// }

	// @Override
	// protected void configure(AuthenticationManagerBuilder builder) throws
	// Exception {
	// builder.userDetailsService(ssUserDetailsService).passwordEncoder(new
	// BCryptPasswordEncoder());
	//
	// }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests().antMatchers("/card");
		http.anonymous().and().authorizeRequests().antMatchers("/card/**").permitAll();
		// http.antMatcher("/guest/**").authorizeRequests().anyRequest().permitAll();

	}
}