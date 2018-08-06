package com.loop.fidelicard.security.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loop.fidelicard.security.dto.LoginUserDTO;
import com.loop.fidelicard.security.repository.LoginUserRepository;
import com.loop.fidelicard.service.ErrorsService;
import com.loop.fidelicard.util.PasswordUtils;

@Service
public class LoginUserService {
	@Autowired
	LoginUserRepository loginUserRepository;

	// @Autowired
	// WebSecurityConfig configSecurity;

	// @Autowired
	// private MyUserDetailService myUserDetailService;

	@Autowired
	private ErrorsService eS;

	public List<LoginUser> findAll() {
		return (List<LoginUser>) loginUserRepository.findAll();
	}

	public LoginUser save(LoginUserDTO loginUserDTO) {
		LoginUser loginUser = loginUserFromLoginUserDTO(loginUserDTO);
		return save(loginUser);
	}

	public void removeCredentials(String username) {
		// configSecurity.inMemoryUserDetailsManager().deleteUser(username);
	}

	public LoginUser save(LoginUser loginUser) {
		loginUser.setPassword(PasswordUtils.gerarBCrypt(loginUser.getPassword()));

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

	public List<String> errorsToSave(LoginUserDTO loginUserDTO) {
		List<String> errors = new ArrayList<String>();

		eS.addErrorsIfLoginUserByEmailExist(loginUserDTO.getLoginUserEmail(), errors);

		return errors;
	}

	public LoginUser loginUserFromLoginUserDTO(LoginUserDTO loginUserDTO) {
		return LoginUser.builder().email(loginUserDTO.getLoginUserEmail()).password(loginUserDTO.getLoginUserPassword())
				.userRole(loginUserDTO.getLoginUserRole()).build();
	}
}
