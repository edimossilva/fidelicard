package com.loop.fidelicard.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loop.fidelicard.security.dto.LoginUserDTO;
import com.loop.fidelicard.security.dto.ResponseLoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.security.service.LoginUserService;
import com.loop.fidelicard.util.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginUserServiceTest {
	@Autowired
	LoginUserService loginUserService;

	@Test
	public void saveTest() {

		String email = "enterprise34@gmail.com";
		String password = "secretword";

		UserRole userRole = UserRole.ENTERPRISE;
		LoginUserDTO loginUserDTO = new LoginUserDTO();
		loginUserDTO.setLoginUserEmail(email);
		loginUserDTO.setLoginUserPassword(password);
		loginUserDTO.setLoginUserRole(userRole);

		ResponseLoginUserDTO responseLoginUserDTO = loginUserService.save(loginUserDTO).toResponseLoginUserDTO();

		String encryptedPassword = PasswordUtils.gerarBCrypt(password);
		LoginUser expectedLoginUser = new LoginUser();
		expectedLoginUser.setId(responseLoginUserDTO.getId());
		expectedLoginUser.setEmail(email);
		expectedLoginUser.setPassword(encryptedPassword);
		expectedLoginUser.setUserRole(userRole);
		ResponseLoginUserDTO expectedResponseLoginUserDTO = expectedLoginUser.toResponseLoginUserDTO();

		assertEquals(responseLoginUserDTO, expectedResponseLoginUserDTO);

	}
}
