package com.loop.fidelicard.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.jwt.filter.WebSecurityConfig;
import com.loop.fidelicard.security.dto.LoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.repository.LoginUserRepository;

@Service
public class LoginUserService {
	@Autowired
	LoginUserRepository loginUserRepository;

	@Autowired
	WebSecurityConfig configSecurity;

	@Autowired
	private MyUserDetailService myUserDetailService;

	public List<LoginUser> findAll() {
		return (List<LoginUser>) loginUserRepository.findAll();
	}

	public LoginUser save(LoginUserDTO loginUserDTO) {
		LoginUser loginUser = myUserDetailService.userDetailFromLoginUserDTO(loginUserDTO);
		myUserDetailService.giveCredentials(loginUser, configSecurity.inMemoryUserDetailsManager());
		loginUser = loginUserRepository.save(loginUser);
		return loginUser;
	}

	public void removeCredentials(String username) {
		configSecurity.inMemoryUserDetailsManager().deleteUser(username);
	}

	public LoginUser save(LoginUser loginUser) {
		// String password = PasswordUtils.gerarBCrypt(loginUser.getPassword());
		// loginUser.setPassword(password);
		myUserDetailService.giveCredentials(loginUser, configSecurity.inMemoryUserDetailsManager());
		return loginUserRepository.save(loginUser);
	}

	public void deleteById(Long id) {
		loginUserRepository.deleteById(id);
	}

	public LoginUser findByEmail(String email) {
		return loginUserRepository.findByEmail(email);
	}

	public LoginUser findById(Long loginUserId) {
		

		return loginUserRepository.findById(loginUserId);
	}

	public void update(LoginUser loginUser) {
		loginUserRepository.save(loginUser);
	}
}
