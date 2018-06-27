package com.loop.fidelicard.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.security.ConfigSecurity;
import com.loop.fidelicard.security.dto.LoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.security.repository.LoginUserRepository;
import com.loop.fidelicard.util.PasswordUtils;

@Service
public class LoginUserService {
	@Autowired
	LoginUserRepository loginUserRepository;
	@Autowired
	ConfigSecurity configSecurity;
	@Autowired
	private InMemoryUserDetailsManager inMemoryUserDetailsManager;

	public List<LoginUser> findAll() {
		return loginUserRepository.findAll();
	}

	public LoginUser save(LoginUserDTO loginUserDTO) {
		String password = PasswordUtils.gerarBCrypt(loginUserDTO.getPassword());
		LoginUser loginUser = LoginUser.builder().email(loginUserDTO.getEmail()).password(password)
				.userRole(loginUserDTO.getUserRole()).build();

		inMemoryUserDetailsManager.createUser(
				new User(loginUser.getEmail(), loginUser.getPassword(), getAuthorities(loginUser.getUserRole())));

		return loginUserRepository.save(loginUser);
	}

	private static List<GrantedAuthority> getAuthorities(UserRole userRole) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole));
		return authorities;
	}

	public LoginUser save(LoginUser loginUser) {
		String password = PasswordUtils.gerarBCrypt(loginUser.getPassword());
		loginUser.setPassword(password);
		return loginUserRepository.save(loginUser);
	}

	public void deleteById(Long id) {
		loginUserRepository.deleteById(id);
	}

	public LoginUser findByEmail(String email) {
		return loginUserRepository.findAllByEmail(email);
	}
}
