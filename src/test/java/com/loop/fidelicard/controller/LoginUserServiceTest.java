package com.loop.fidelicard.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.loop.fidelicard.security.dto.LoginUserDTO;
import com.loop.fidelicard.security.model.LoginUser;
import com.loop.fidelicard.security.model.UserRole;
import com.loop.fidelicard.security.service.LoginUserService;
import com.loop.fidelicard.util.PasswordUtils;

@DataJpaTest
@AutoConfigureBefore(DataSourceAutoConfiguration.class)

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginUserServiceTest {
	@MockBean
	LoginUserService loginUserService;

	@Test
	public void saveTest() {
		// = mock(LoginUserService.class);

//		String email = "enterprise34@gmail.com";
//		String password = "secretword";
//
//		UserRole userRole = UserRole.ENTERPRISE;
//		LoginUserDTO loginUserDTO = LoginUserDTO.builder().email(email).password(password).userRole(userRole).build();
//
//		LoginUser loginUser = loginUserService.save(loginUserDTO);
//
//		String encryptedPassword = PasswordUtils.gerarBCrypt(password);
//		LoginUser expectedLoginUser = LoginUser.builder().email(email).password(encryptedPassword).userRole(userRole)
//				.build();
//
//		assertEquals(expectedLoginUser, loginUser);

	}
}
