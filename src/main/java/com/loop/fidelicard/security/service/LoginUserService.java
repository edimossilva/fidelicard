package com.loop.fidelicard.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.security.dto.LoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.repository.LoginUserRepository;
import com.loop.fidelicard.util.SenhaUtils;

@Service
public class LoginUserService {
	@Autowired
	LoginUserRepository loginUserRepository;

	public List<LoginUser> findAll() {
		return loginUserRepository.findAll();
	}

	public LoginUser save(LoginUserDTO loginUserDTO) {
		String password = SenhaUtils.gerarBCrypt(loginUserDTO.getPassword());
		LoginUser loginUser = LoginUser.builder().email(loginUserDTO.getEmail()).password(password)
				.userRole(loginUserDTO.getUserRole()).build();
		return loginUserRepository.save(loginUser);
	}

	public LoginUser save(LoginUser loginUser) {
		String password = SenhaUtils.gerarBCrypt(loginUser.getPassword());
		loginUser.setPassword(password);
		return loginUserRepository.save(loginUser);
	}
}
