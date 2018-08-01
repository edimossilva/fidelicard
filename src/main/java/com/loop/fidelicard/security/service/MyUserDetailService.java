package com.loop.fidelicard.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.security.dto.LoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;

@Service
public class MyUserDetailService {
	@Autowired
	private LoginUserService loginUserService;

	public Properties getAll() {
		Properties users = new Properties();
		for (LoginUser loginUser : loginUserService.findAll()) {
			String credentials = loginUser.getPassword() + ",ROLE_" + loginUser.getUserRole() + ",enabled";
			users.put(loginUser.getEmail(), credentials);
		}
		return users;
	}

	public LoginUser userDetailFromLoginUserDTO(LoginUserDTO loginUserDTO) {
		return LoginUser.builder().email(loginUserDTO.getEmail()).password(loginUserDTO.getPassword())
				.userRole(loginUserDTO.getUserRole()).build();
	}

	public void giveCredentials(LoginUser loginUser, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
		String email = loginUser.getEmail();
		String password = loginUser.getPassword();
		List<GrantedAuthority> authorities = getAuthorities(loginUser.getUserRole());
		User user = new User(email, password, authorities);
		inMemoryUserDetailsManager.createUser(user);
	}

	private static List<GrantedAuthority> getAuthorities(UserRole userRole) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole));
		return authorities;
	}
}
