package com.loop.fidelicard.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.security.ConfigSecurity;
import com.loop.fidelicard.security.dto.LoginUserDTO;
import com.loop.fidelicard.security.dto.ResponseLoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.repository.LoginUserRepository;
import com.loop.fidelicard.util.PasswordUtils;

@Service
public class LoginUserService {
	@Autowired
	LoginUserRepository loginUserRepository;

	@Autowired
	ConfigSecurity configSecurity;

	@Autowired
	private MyUserDetailService myUserDetailService;

	public List<LoginUser> findAll() {
		return loginUserRepository.findAll();
	}

	public ResponseLoginUserDTO save(LoginUserDTO loginUserDTO) {
		LoginUser loginUser = myUserDetailService.userDetailFromLoginUserDTO(loginUserDTO);
		myUserDetailService.giveCredentials(loginUser, configSecurity.inMemoryUserDetailsManager());
		// System.out.println("LOGINUSER" + loginUserDTO);
		loginUser = loginUserRepository.save(loginUser);
		return loginUser.toResponseLoginUserDTO();
	}

	public LoginUser save(LoginUser loginUser) {
		String password = PasswordUtils.gerarBCrypt(loginUser.getPassword());
		loginUser.setPassword(password);
		myUserDetailService.giveCredentials(loginUser, configSecurity.inMemoryUserDetailsManager());

		return loginUserRepository.save(loginUser);
	}

	public void deleteById(Long id) {
		loginUserRepository.deleteById(id);
	}

	public LoginUser findByEmail(String email) {
		return loginUserRepository.findAllByEmail(email);
	}

	public LoginUser findById(Long loginUserId) {
		return loginUserRepository.findById(loginUserId).get();
	}
}
